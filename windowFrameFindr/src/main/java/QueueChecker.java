import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIOptions;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

/**
 * Created by Ludvig Fröberg on 2015-10-03.
 */
public class QueueChecker {


    private static final int msize = 30;
    private static int[][] m = new int[msize][msize];
    private static String lastWord = "";
    private static boolean initiated = false;
    private static WinDef.RECT aRect = new WinDef.RECT();
    private static Rectangle wRect;
    private static int cbsuccesses = 0;
    private static int cbwaittime = 50;
    private static final double epsilon = 0.80;

    private static final int MAX_TITLE_LENGTH = 1024;


    private static BufferedImage cap;
    private static Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static DataFlavor[] flavors;
    private static Tesseract t = Tesseract.getInstance();

    private static Robot robot;


    private static void initiate() {
        initiated = true;
        for (int i = 0; i < msize; i++) {
            m[0][i] = i;
            m[i][0] = i;
        }
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            initiated = false;
        }
        t.setDatapath("src/main/resources");
    }

    public static boolean queuePopped(Game game) {
        if (!initiated) {
            //initiate
            QueueChecker.initiate();
        }
        String cmpString = Encoding.getAcceptStringFromGame(game);
        String ocrString = ocrCheck(game);
        return closeEnough(ocrString, cmpString);
    }

    private static String ocrCheck(Game game) {
        WinDef.HWND windowHandle = User32DLL.GetForegroundWindow();
        User32DLL.GetWindowRect(windowHandle, aRect);
        wRect = aRect.toRectangle();
        int sy = 0;
        int sx = 0;
        int sw = 0;
        int sh = 0;
        int extraY = getExtraY(wRect);
        wRect.y = (int) (wRect.getY() + extraY);
        wRect.height = (int) (wRect.getHeight() - extraY);
        switch (game) {
            case Dota2:
                sy = (int) (wRect.getY() + wRect.getHeight()*(420.0/1200.0));
                sh = (int) (wRect.getHeight()*(30.0/1200.0) );
                sx = (int) (wRect.getX() + wRect.getWidth()*(830.0/1920.0));
                sw = (int) (wRect.getWidth()*(260.0/1920.0));
                break;
            case CSGO:
                sy = (int) (wRect.getY() + wRect.getHeight()*(190.0/1200.0));
                sh = (int) (wRect.getHeight()*(40.0/1200.0) );
                sx = (int) (wRect.getX() + (wRect.getWidth()-(wRect.getHeight()*1.25))/2.0 + (380.0/1080.0)*wRect.getHeight()/2 - 0.23*wRect.getHeight()/2);
                sw = (int) (0.23*wRect.getHeight());
                break;
            case LoL:
                sy = (int) (wRect.getY() + (wRect.getHeight()-167)/2 + 21);
                sh = (int) 25;
                sx = (int) (wRect.getX() + (wRect.getWidth()-370)/2 + 120);
                sw = (int) 134;
                break;
            default:
                break;
        }

        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
        robot.delay(1); // 1 ms
        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
        robot.delay(cbwaittime); // 30 ms

        try {
            cap = (BufferedImage)cb.getData(DataFlavor.imageFlavor); //ca 40ms
            cbsuccesses++;
            if (cbsuccesses > 100) {
                cbwaittime--;
            }
            cap = cap.getSubimage(sx,sy,sw,sh);
            String ocrString = t.doOCR(cap);

            //dev lines below to see what image is caught
            //File file = new File(Encoding.getStringFromGame(game)+"OCRCap.png");
            //ImageIO.write(cap, "png", file);
            //System.out.println(ocrString);
            return ocrString.trim();
        } catch (Exception e) {
            cbsuccesses = 0;
            if (cbwaittime < 200) {
                cbwaittime++;
            }
            e.printStackTrace();
            System.out.println("ojsan");
        }
        return "ocr failure";
    }

    private static int getExtraY(Rectangle r) {
        if (r.getWidth()/r.getHeight() == 16.0/9.0 ||
                r.getWidth()/r.getHeight() == 16.0/10.0 ||
                r.getWidth()/r.getHeight() == 4.0/3.0) {
            return 0;
        } else {
            return 26;
        }
    }

    public static boolean foregroundWindowMatches(Game game) {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        User32DLL.GetWindowTextW(User32DLL.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        String winTitle = Native.toString(buffer);
        //System.out.println("foreground window: "+winTitle);
        return winTitle.equalsIgnoreCase(Encoding.getWindowTitleFromGame(game));
    }

    private static int matchesToIndex(String w1, String w2) {
        int i = 0;
        for (; i < (w1.length() < w2.length() ? w1.length() : w2.length()); i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                break;
            }
        }
        return i;
    }

    private static boolean closeEnough(String w1, String w2) {
        int tolerance = (int) (epsilon*Math.max(w1.length(), w2.length()));
        if (Math.abs(w1.length()-w2.length()) > tolerance) {
            return false;
        }
        w1 = w1.toLowerCase();
        w2 = w2.toLowerCase();
        int matches = matchesToIndex(w2, lastWord);
        for (int i = 1; i <= w1.length(); i++) {
            for (int j = matches+1; j <= w2.length(); j++) {

                if (w1.charAt(i-1) == w2.charAt(j-1)) { // 0
                    m[i][j] = m[i-1][j-1];
                } else { // 1
                    int y =  Math.min(m[i][j-1], m[i-1][j]);
                    m[i][j] = Math.min(y, m[i-1][j-1]) + 1;
                }

            }
        }
        lastWord = w2;
        if (m[w1.length()][w2.length()] <= tolerance) {
            return true;
        } else {
            return false;
        }
    }







    /// Supporting DLL Definitions

    public interface WinGDIExtra extends WinGDI {

        public WinDef.DWORD SRCCOPY = new WinDef.DWORD(0x00CC0020);

    }

    public interface User32Extra extends User32 {

        User32Extra INSTANCE = (User32Extra) Native.loadLibrary("user32", User32Extra.class, W32APIOptions.DEFAULT_OPTIONS);

        public HDC GetWindowDC(HWND hWnd);

        public boolean GetClientRect(HWND hWnd, RECT rect);

    }

    public interface GDI32Extra extends GDI32 {

        GDI32Extra INSTANCE = (GDI32Extra) Native.loadLibrary("gdi32", GDI32Extra.class, W32APIOptions.DEFAULT_OPTIONS);

        public boolean BitBlt(WinDef.HDC hObject, int nXDest, int nYDest, int nWidth, int nHeight, WinDef.HDC hObjectSource, int nXSrc, int nYSrc, WinDef.DWORD dwRop);

    }

    static class Kernel32 {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(WinDef.HWND hWnd, PointerByReference pref);
        public static native WinDef.HWND GetForegroundWindow();
        public static native WinDef.BOOL GetWindowRect(WinDef.HWND hwnd, WinDef.RECT rct);
        public static native int GetWindowTextW(WinDef.HWND hWnd, char[] lpString, int nMaxCount);
        public static native WinDef.BOOL PrintWindow(WinDef.HWND hWnd, WinDef.HDC hdcBlt, int flags);
    }




}
