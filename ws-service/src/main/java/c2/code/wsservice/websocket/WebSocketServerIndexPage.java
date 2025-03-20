/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package c2.code.wsservice.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

/**
 * Generates the demo HTML page which is served at http://localhost:8181/
 */
public final class WebSocketServerIndexPage {

    private static final String NEWLINE = "\r\n";
    private static final String CONTENT = "<html><head><title>Web Socket Test</title></head>" + NEWLINE
            + "<body>" + NEWLINE
            + "<script type=\"text/javascript\">" + NEWLINE
            + "var socket;" + NEWLINE
            + "if (!window.WebSocket) {" + NEWLINE
            + "  window.WebSocket = window.MozWebSocket;" + NEWLINE
            + '}' + NEWLINE
            + "if (window.WebSocket) {" + NEWLINE
            + "  socket = new WebSocket(\"" + "/ws" + "\");" + NEWLINE
            + "  socket.onmessage = function(event) {" + NEWLINE
            + "    var ta = document.getElementById('responseText');" + NEWLINE
            + "    ta.value = ta.value + '\\n' + event.data" + NEWLINE
            + "  };" + NEWLINE
            + "  socket.onopen = function(event) {" + NEWLINE
            + "    var ta = document.getElementById('responseText');" + NEWLINE
            + "    ta.value = \"Web Socket opened!\";" + NEWLINE
            + "  };" + NEWLINE
            + "  socket.onclose = function(event) {" + NEWLINE
            + "    var ta = document.getElementById('responseText');" + NEWLINE
            + "    ta.value = ta.value + \"Web Socket closed\"; " + NEWLINE
            + "  };" + NEWLINE
            + "} else {" + NEWLINE
            + "  alert(\"Your browser does not support Web Socket.\");" + NEWLINE
            + '}' + NEWLINE
            + NEWLINE
            + "function send(message) {" + NEWLINE
            + "  if (!window.WebSocket) { return; }" + NEWLINE
            + "  if (socket.readyState == WebSocket.OPEN) {" + NEWLINE
            + "    socket.send(message);" + NEWLINE
            + "  } else {" + NEWLINE
            + "    alert(\"The socket is not open.\");" + NEWLINE
            + "  }" + NEWLINE
            + '}' + NEWLINE
            + "</script>" + NEWLINE
            + "<form onsubmit=\"return false;\">" + NEWLINE
            + "<input type=\"text\" name=\"message\" value=\"Hello, World!\"/>"
            + "<input type=\"button\" value=\"Send Web Socket Data\"" + NEWLINE
            + "       onclick=\"send(this.form.message.value)\" />" + NEWLINE
            + "<h3>Output</h3>" + NEWLINE
            + "<textarea id=\"responseText\" style=\"width:500px;height:300px;\"></textarea>" + NEWLINE
            + "</form>" + NEWLINE
            + "</body>" + NEWLINE
            + "</html>" + NEWLINE;

    public static ByteBuf getContent(String webSocketLocation) {
        byte[] content = new byte[]{};
        try {
            File resource = new ClassPathResource("index.html").getFile();
            content = Files.readAllBytes(resource.toPath());
        } catch (Exception e) {
        }
        if (content.length > 0) {
            return Unpooled.copiedBuffer(content);
        } else {
            return Unpooled.copiedBuffer(CONTENT, CharsetUtil.US_ASCII);
        }

    }

    public static ByteBuf getMessage(String location) {
        String fileName = location.substring(location.lastIndexOf("/") + 1);
        byte[] content = new byte[]{};
        try {
            File resource = new ClassPathResource("message/" + fileName).getFile();
            content = Files.readAllBytes(resource.toPath());
//            content = (fileName.substring(0, 4) + new String(content)).getBytes();
        } catch (Exception e) {
        }
        if (content.length > 0) {
            return Unpooled.copiedBuffer(content);
        } else {
            return Unpooled.copiedBuffer(CONTENT, CharsetUtil.US_ASCII);
        }

    }

    private WebSocketServerIndexPage() {
        // Unused
    }
}
