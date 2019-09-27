package com.dantefung.algorithm.lb;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/16 17:27
 */
public class ServerInfo {

    public String ip;

    private int port;

    public ServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
