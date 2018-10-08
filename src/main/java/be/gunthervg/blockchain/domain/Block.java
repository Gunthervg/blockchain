package be.gunthervg.blockchain.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Slf4j
public class Block {

    private String hash, previousHash, data;
    private long timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    private int nonce;

    @Builder
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
        this.mineBlock(BlockChain.DIFFICULTY);
    }

    public String calculateHash() {
        String s = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data;
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with DIFFICULTY * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        log.debug("Block Mined: {}", hash);
    }
}
