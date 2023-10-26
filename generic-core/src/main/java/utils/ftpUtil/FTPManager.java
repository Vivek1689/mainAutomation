package utils.ftpUtil;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FTPManager {

    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;

    public FTPManager(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public void open() {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        try {
            ftp.connect(server, port);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new RuntimeException("Exception in connecting to FTP Server");
            }
            ftp.login(user, password);
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect : " + e);
        }
    }

    public void close() throws IOException {
        ftp.disconnect();
    }

    public Collection<String> listFiles(String path) {
        FTPFile[] files;
        try {
            files = ftp.listFiles(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    public void downloadFile(String source, String destination) {
        try {
            FileOutputStream out = new FileOutputStream(destination);
            ftp.retrieveFile(source, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFileToPath(File file, String path){
        try {
            ftp.storeFile(path, new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
