package org.testcontainers.dockerclient;

import org.rnorth.tcpunixsocketproxy.TcpToUnixSocketProxy;

import com.github.dockerjava.core.DefaultDockerClientConfig;

import java.io.File;

public class SocketClientProviderStrategyForWindows extends UnixSocketClientProviderStrategy {

    @Override
    public void test() throws InvalidConfigurationException {

        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            throw new InvalidConfigurationException("This strategy is only applicable to Windows.");
        }

        //TcpToUnixSocketProxy proxy = new TcpToUnixSocketProxy(new File(DOCKER_SOCK_PATH));

        try {
        	config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost("tcp://localhost:2375")
                    .withDockerTlsVerify(false)
                    .build();
            client = getClientForConfig(config);

           // LOGGER.info("Accessing Docker for Mac unix domain socket via TCP proxy (" + DOCKER_SOCK_PATH + " via localhost:" + proxyPort + ")");
        } catch (Exception e) {

            //proxy.stop();

            throw new InvalidConfigurationException("ping failed", e);
        }

    }

    @Override
    public String getDescription() {
        return "local Unix socket (via TCP proxy)";
    }

}
