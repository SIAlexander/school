package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Profile("test")
public class InfoServiceImplTest implements InfoService {

    @Value("${server.port}")
    private Integer port;
    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Override
    public Integer getPort(){
        logger.info("Was invoked method for getPort");
        return port;
    }

    @Override
    public Integer getIntegerValue(){
        return null;
    }
}
