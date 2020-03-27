package org.quanquanxu.tankwar;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameClientTest {

    @Test
    void save() throws IOException {
        GameClient.getInstance().save();
        byte[] bytes = Files.readAllBytes(Paths.get("game.sav"));
        Save save = JSON.parseObject(bytes, Save.class);
        assertTrue(save.isGameContinued());
        assertEquals(15,save.getEnemyTankPositions().size());
    }
}