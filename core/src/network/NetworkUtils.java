package network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.PatternSyntaxException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.mygdx.game.util.CameraController;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;

public class NetworkUtils {
	public static HantoClient hantoClient;
	public static HantoServer hantoServer;
	public static boolean enterGame = false;
	public static String serverIP = NetworkUtils.getIpAddress();
	public static CameraController cameraController;
	public static String getIpAddress() { 
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        String ipAddress=inetAddress.getHostAddress().toString();
                        System.out.println("IP address" + ""+ipAddress);
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        	System.out.println("Socket exception in GetIP Address of Utilities"+ ex.toString());
        }
        return null; 
	}
}
