package passwordManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


class Website {
    private String url;
    private String username;
    private String password;
    static String DELIMITER = "\n";

    Website(String url, String username, String password) {
        this.setUrl(url);
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * Returns the path of the file where the class is saved
     * 
     * @param url to search for
     * @return the path of the file
     */
    public static String getFilePath(String url) {
        return "./bin/" + url;
    }

    /**
     * Searches the file with name URL and reads it to a Website class
     * 
     * @param url the filename to search for
     * @return Website that is written to the file
     */
    public static Website findWebsite(String url, String encryptionPassword) {
        File file = new File(Website.getFilePath(url));
        Encryption encrypt = new Encryption(encryptionPassword);
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] input = new byte[(int) file.length()];
            in.read(input);
            in.close();

            String content = encrypt.decrypt(input);
            String[] contentArr = content.split(Website.DELIMITER);
            return new Website(url, contentArr[0], contentArr[1]);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * write to a file with the url name
     */
    public void save(String encryptionPassword) throws IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        File file = new File(Website.getFilePath(this.url));
        file.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(file);
        String data = this.username + Website.DELIMITER + this.password;
        Encryption encrypt = new Encryption(encryptionPassword);
        fileWriter.write(encrypt.encrypt(data));
        fileWriter.close();
    }
    
    /**
     * deletes the file with the url name
     */
    public void delete(){
        File file = new File(Website.getFilePath(this.url));
        file.delete();
    }

    /**
     * checks if the current version of the website is saved
     * @return true if saved else false.
     */
    public boolean isCurrentVersionSaved(String encryptionPassword){
        Website web = Website.findWebsite(this.url, encryptionPassword);
        
        if (web == null) {
            return false;
        }

        return web.equals(this);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}