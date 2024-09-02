/*
Colby Bray

Networking 331-001
This class serves as the constructor for the send/receive methods
*/

import java.io.IOException;

public interface User {
    void sendMessage() throws IOException;
    int receiveMessage() throws IOException;
}