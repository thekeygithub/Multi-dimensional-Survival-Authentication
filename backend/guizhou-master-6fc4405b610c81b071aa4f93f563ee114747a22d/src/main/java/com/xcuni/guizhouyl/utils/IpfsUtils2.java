package com.xcuni.guizhouyl.utils;



import com.alibaba.fastjson.JSON;

import com.xcuni.guizhouyl.model.IpfsJsonData;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.cid.Cid;
import io.ipfs.multiaddr.MultiAddress;


import java.io.IOException;

public class IpfsUtils2 {
    private static IPFS ipfs=new IPFS(new MultiAddress("/ip4/192.168.70.157/tcp/5001"));

    public static String upload(String inputInfo) throws IOException {
        IpfsJsonData jsonobj = new IpfsJsonData();
        jsonobj.setData(inputInfo);
        String input = JSON.toJSONString(jsonobj);
        ipfs.refs.local();
        byte[] object = input.getBytes();
        MerkleNode put = ipfs.dag.put("json", object);
        return put.hash.toString();
    }
    public static String download(String hash) throws IOException {
        ipfs.refs.local();
        Cid expected = Cid.decode(hash);
        byte[] res=ipfs.dag.get(expected);
        IpfsJsonData parse = JSON.parseObject(res, IpfsJsonData.class);
        //return new String(res);
        return parse.getData();
    }

}
