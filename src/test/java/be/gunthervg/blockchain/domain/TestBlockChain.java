package be.gunthervg.blockchain.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class TestBlockChain {

    @Test
    public void validChain() {
        Block firstBlock = Block.builder().data("First block").previousHash("0").build();
        Block secondBlock = Block.builder().data("Second block").previousHash(firstBlock.getHash()).build();
        Block thirdBlock = Block.builder().data("Third block").previousHash(secondBlock.getHash()).build();

        BlockChain blockChain = BlockChain.builder().block(firstBlock).block(secondBlock).block(thirdBlock).build();

        log.debug(blockChain.toString());

        assertTrue(blockChain.isValid());
    }

    @Test
    public void invalidChain() {
        Block firstBlock = Block.builder().data("First block").previousHash("0").build();
        Block secondBlock = Block.builder().data("Second block").previousHash("random hash").build();
        Block thirdBlock = Block.builder().data("Third block").previousHash(secondBlock.getHash()).build();

        BlockChain blockChain = BlockChain.builder().block(firstBlock).block(secondBlock).block(thirdBlock).build();

        assertFalse(blockChain.isValid());
    }
}