package cn.dengyongsheng.smartcar_control;


import cn.dengyongsheng.smartcar_control.Instructions.InstructionBase;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;

public class ControlUtils extends Thread {
    private static final int port = 1392;
    public static final ArrayBlockingQueue<DatagramPacket> datagramPacketQueue = new ArrayBlockingQueue<DatagramPacket>(80);

    static {
        ControlUtils controlUtils = new ControlUtils();
        controlUtils.start();
    }

    /**
     * 获取局域网广播地址
     */
    public static String getBroadcast() throws SocketException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements(); ) {
            NetworkInterface ni = niEnum.nextElement();
            if (!ni.isLoopback()) {
                // 遍历网卡
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {
                    // IP地址以192开头
                    String ip = interfaceAddress.getAddress().toString();
                    if (ip.startsWith("/192.") &&
                            interfaceAddress.getBroadcast() != null) {
                        return interfaceAddress.getBroadcast().toString().substring(1);
                    }
                }
            }
        }
        return "广播地址获取失败";
    }

    public static void putInstrution(String ip, InstructionBase instructionBase) throws IOException {
        byte[] instructionBytes = instructionBase.toString().getBytes(StandardCharsets.UTF_8);
        DatagramPacket datagramPacket = new DatagramPacket(instructionBytes, instructionBytes.length, InetAddress.getByName(ip), port);
        datagramPacketQueue.add(datagramPacket);
    }

    public static void putInstrution(InstructionBase instructionBase) throws IOException {
        putInstrution("255.255.255.255", instructionBase);
    }


    public void run() {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            while (true) {
                try {
                    Thread.sleep(10);
                    DatagramPacket datagramPacket = null;
                    synchronized (datagramPacketQueue) {
                        if (!datagramPacketQueue.isEmpty()) {
                            datagramPacket = datagramPacketQueue.take();
                        }
                    }
                    // 发送指令数据
                    if (datagramPacket != null) {
                        datagramSocket.send(datagramPacket);
                    }
                } catch (InterruptedException e) {
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
