package com.xcuni.guizhouyl.utils;


import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.cid.Cid;
import io.ipfs.multiaddr.MultiAddress;


import java.io.IOException;

public class IpfsUtils {
    private static IPFS ipfs = new IPFS(new MultiAddress("/ip4/47.94.241.242/tcp/5001"));

    public static String upload(String inputInfo) throws IOException {
        ipfs.refs.local();
        byte[] object = inputInfo.getBytes();
        MerkleNode put = ipfs.dag.put("json", object);
        return put.hash.toString();
    }

    public static String download(String hash) throws IOException {
        ipfs.refs.local();
        Cid expected = Cid.decode(hash);
        byte[] res = ipfs.dag.get(expected);
        return new String(res);
    }
}
