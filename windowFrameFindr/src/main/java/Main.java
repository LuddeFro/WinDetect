/**
 * Created by Ludvig Fröberg on 2015-09-28.
 */



public class Main {

    private static final int MAX_TITLE_LENGTH = 1024;

    public static void main(String[] args) throws Exception {
        Thread.sleep(1000);
        System.out.println(QueueChecker.foregroundWindowMatches(Game.CSGO));
        System.out.println(QueueChecker.foregroundWindowMatches(Game.Dota2));
        System.out.println(QueueChecker.foregroundWindowMatches(Game.LoL));
        System.out.println(QueueChecker.queuePopped(Game.CSGO));
        System.out.println(QueueChecker.queuePopped(Game.LoL));
        System.out.println(QueueChecker.queuePopped(Game.Dota2));
    }



        /*       RECT aRect = new RECT();
        long t1 = System.currentTimeMillis();
        HWND windowHandle = User32DLL.GetForegroundWindow();
        User32DLL.GetWindowRect(windowHandle, aRect);
        System.out.println("getWindowRect time: " + (System.currentTimeMillis() - t1));
        System.out.println(
                "x: " + aRect.toRectangle().getX() +
                ", y: " +aRect.toRectangle().getY()+
                ", w: " + aRect.toRectangle().getWidth()+
                ", h: " + aRect.toRectangle().getHeight()
        );
        Rectangle wRect = aRect.toRectangle();

        String game = "csgo";

        int sy = 0;
        int sx = 0;
        int sw = 0;
        int sh = 0;
        if (game.equals("lol")) { /////      PVP.net Client
            sy = (int) (wRect.getY() + wRect.getHeight()*(335.0/800.0));
            sh = (int) (wRect.getHeight()*(25.0/800.0) );
            sx = (int) (wRect.getX() + wRect.getWidth()*(575.0/1280.0));
            sw = (int) (wRect.getWidth()*(130.0/1280.0));
        } else if (game.equals("dota")) { ///////
            sy = (int) (wRect.getY() + wRect.getHeight()*(420.0/1200.0));
            sh = (int) (wRect.getHeight()*(30.0/1200.0) );
            sx = (int) (wRect.getX() + wRect.getWidth()*(830.0/1920.0));
            sw = (int) (wRect.getWidth()*(260.0/1920.0));
        } else { // csgo   /////////    Counter-Strike: Global Offensive

            sy = (int) (wRect.getY() + wRect.getHeight()*(190.0/1200.0));
            sh = (int) (wRect.getHeight()*(40.0/1200.0) );
            sx = (int) (wRect.getX() + wRect.getWidth()*(280.0/1920.0));
            sw = (int) (wRect.getX() + wRect.getWidth()*(280.0/1920.0));
            /*sy = (int) (wRect.getY() + wRect.getHeight()*(0/1200.0));
            sh = (int) (wRect.getHeight()*(1200/1200.0) );
            sx = (int) (wRect.getX() + wRect.getWidth()*(0/1920.0));
            sw = (int) (wRect.getX() + wRect.getWidth()*(1920/1920.0));
        *//*
        }

///////////////////
        BufferedImage cap;
        Clipboard cb;
        DataFlavor[] flavors;

        Robot robot = new Robot();
        //////////////////////
        long t2 = System.currentTimeMillis();



        //BufferedImage cap = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        //BufferedImage cap = new Robot().createScreenCapture(new Rectangle(sx, sy, sw, sh));





        try {
*/
            //cap = ImageIO.read(new File("test.png"));
  //          FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(/*":0.0+" + sx + "," + sy*/"title=Counter-Strike: Global Offensive");
            //avformat.av_find_input_format("x11grab");
            /*grabber.setFormat("gdigrab");
            //FFmpegFrameGrabber
            grabber.setFrameRate(1);
            grabber.setOption("-t","1");
            grabber.setImageWidth(sw);
            grabber.setImageHeight(sh);
            grabber.start();

            //Canvas frame = new CanvasFrame("Screen Cap");
            Java2DFrameConverter frconv = new Java2DFrameConverter();
            cap = frconv.convert(grabber.grabImage());
*/

            ////


            //cap = capture(windowHandle);


            ////

/*

            System.out.println("getImage split time1: " + (System.currentTimeMillis() - t2));
            // get the screenshot
            System.out.println("getImage split time1.1: " + (System.currentTimeMillis() - t2));
            robot.keyPress(KeyEvent.VK_PRINTSCREEN);
            System.out.println("getImage split time1.2: " + (System.currentTimeMillis() - t2));
            robot.delay(1); // 10 ms
            robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
            System.out.println("getImage split time1.3: " + (System.currentTimeMillis() - t2));
            robot.delay(30); // 30 ms

            System.out.println("getImage split time2: " + (System.currentTimeMillis() - t2));

            cb = Toolkit.getDefaultToolkit().getSystemClipboard();

            flavors = cb.getAvailableDataFlavors();
            System.out.println("getImage split time4: " + (System.currentTimeMillis() - t2));
            cap = (BufferedImage)cb.getData(DataFlavor.imageFlavor); //ca 40ms
            System.out.println("getImage split timef: " + (System.currentTimeMillis() - t2));
            cap = cap.getSubimage(sx,sy,sw,sh);

            System.out.println("getImage time: " + (System.currentTimeMillis() - t2));
            ////

            Tesseract t = Tesseract.getInstance();
            t.setDatapath("src/main/resources");

            long t3 = System.currentTimeMillis();
            String ss = t.doOCR(cap);
            System.out.println("getText time: " + (System.currentTimeMillis() - t3));
            System.out.println("caught text: "+ ss);

            long t4 = System.currentTimeMillis();
            File file = new File("screencapture.png");
            ImageIO.write(cap, "png", file);
            System.out.println("savfile time: " + (System.currentTimeMillis() - t4));
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ojsan");
        }




        //t 11  == 0.34
        //s 14  == 0.27
        //b 13  == 0.41
        //w 52
        //h 32
        //sw 0.46
        //sh 0.25




        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        User32DLL.GetWindowTextW(User32DLL.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window title: " + Native.toString(buffer));
    }


    public static BufferedImage capture(HWND hWnd) {

        WinDef.HDC hdcWindow = User32.INSTANCE.GetDC(hWnd);
        WinDef.HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

        RECT bounds = new RECT();
        User32Extra.INSTANCE.GetClientRect(hWnd, bounds);

        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);

        WinNT.HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
        GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0, 0, WinGDIExtra.SRCCOPY);

        GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
        GDI32.INSTANCE.DeleteDC(hdcMemDC);

        WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
        bmi.bmiHeader.biWidth = width;
        bmi.bmiHeader.biHeight = -height;
        bmi.bmiHeader.biPlanes = 1;
        bmi.bmiHeader.biBitCount = 32;
        bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

        Memory buffer = new Memory(width * height * 4);
        GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

        GDI32.INSTANCE.DeleteObject(hBitmap);
        User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);

        return image;

    }

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
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native HWND GetForegroundWindow();
        public static native WinDef.BOOL GetWindowRect(HWND hwnd, RECT rct);
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
        public static native WinDef.BOOL PrintWindow(HWND hWnd, WinDef.HDC hdcBlt, int flags);
    }
*/

/*
    public static BufferedImage getScreenshot(Rectangle bounds)
    {
        W32API.HDC windowDC = GDI.GetDC(USER.GetDesktopWindow());
        W32API.HBITMAP outputBitmap = GDI.CreateCompatibleBitmap(windowDC, bounds.width, bounds.height);
        try
        {
            W32API.HDC blitDC = GDI.CreateCompatibleDC(windowDC);
            try
            {
                W32API.HANDLE oldBitmap = GDI.SelectObject(blitDC, outputBitmap);
                try
                {
                    GDI.BitBlt(blitDC, 0, 0, bounds.width, bounds.height, windowDC, bounds.x, bounds.y, GDI32.SRCCOPY);
                }
                finally
                {
                    GDI.SelectObject(blitDC, oldBitmap);
                }
                GDI32.BITMAPINFO bi = new GDI32.BITMAPINFO(40);
                bi.bmiHeader.biSize = 40;
                boolean ok = GDI.GetDIBits(blitDC, outputBitmap, 0, bounds.height, (byte[]) null, bi, GDI32.DIB_RGB_COLORS);
                if (ok)
                {
                    GDI32.BITMAPINFOHEADER bih = bi.bmiHeader;
                    bih.biHeight = -Math.abs(bih.biHeight);
                    bi.bmiHeader.biCompression = 0;
                    return bufferedImageFromBitmap(blitDC, outputBitmap, bi);
                }
                else
                {
                    return null;
                }
            }
            finally
            {
                GDI.DeleteObject(blitDC);
            }
        }
        finally
        {
            GDI.DeleteObject(outputBitmap);
        }
    }

    private static BufferedImage bufferedImageFromBitmap(GDI32.HDC blitDC, GDI32.HBITMAP outputBitmap, GDI32.BITMAPINFO bi)
    {
        GDI32.BITMAPINFOHEADER bih = bi.bmiHeader;
        int height = Math.abs(bih.biHeight);
        final ColorModel cm;
        final DataBuffer buffer;
        final WritableRaster raster;
        int strideBits = (bih.biWidth * bih.biBitCount);
        int strideBytesAligned = (((strideBits - 1) | 0x1F) + 1) >> 3;
        final int strideElementsAligned;
        switch (bih.biBitCount)
        {
            case 16:
                strideElementsAligned = strideBytesAligned / 2;
                cm = new DirectColorModel(16, 0x7C00, 0x3E0, 0x1F);
                buffer = new DataBufferUShort(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned, ((DirectColorModel) cm).getMasks(), null);
                break;
            case 32:
                strideElementsAligned = strideBytesAligned / 4;
                cm = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF);
                buffer = new DataBufferInt(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned, ((DirectColorModel) cm).getMasks(), null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported bit count: " + bih.biBitCount);
        }
        final boolean ok;
        switch (buffer.getDataType())
        {
            case DataBuffer.TYPE_INT:
            {
                int[] pixels = ((DataBufferInt) buffer).getData();
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            case DataBuffer.TYPE_USHORT:
            {
                short[] pixels = ((DataBufferUShort) buffer).getData();
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            default:
                throw new AssertionError("Unexpected buffer element type: " + buffer.getDataType());
        }
        if (ok)
        {
            return new BufferedImage(cm, raster, false, null);
        }
        else
        {
            return null;
        }
    }

    private static final User32 USER = User32.INSTANCE;

    private static final GDI32 GDI = GDI32.INSTANCE;

}

interface GDI32 extends com.sun.jna.platform.win32.GDI32
{
    GDI32 INSTANCE = (GDI32) Native.loadLibrary(GDI32.class);

    boolean BitBlt(WinDef.HDC hdcDest, int nXDest, int nYDest, int nWidth, int nHeight, WinDef.HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);

    WinDef.HDC GetDC(HWND hWnd);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, byte[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, short[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, int[] pixels, WinGDI.BITMAPINFO bi, int usage);

    int SRCCOPY = 0xCC0020;
}
*/

}
