package com.vertexid.commons.crypto;

import com.vertexid.viself.security.RSAKeyVO;
import junit.framework.TestCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class RSACryptographicUtilTest extends TestCase {

    public void testCreateSign() {


        RSACryptographicUtil util = new RSACryptographicUtil();

        RSAKeyVO rsaKeyVO = util.genRsaKeys();
        rsaKeyVO.setToStringStyleStr("MULTI_LINE_STYLE");
        PrivateKey privateKey = rsaKeyVO.getPrivateKey();
        PublicKey publicKey = rsaKeyVO.getPublicKey();

        util.toStringPrivateKey(privateKey);

        System.out.println("CREATE Private Key......"+ rsaKeyVO.toString());
        System.out.println("CREATE Private Key......"+ util.toStringPrivateKey(privateKey));
        System.out.println("CREATE publicKey Key......"+ util.toStringPublicKey(publicKey));

        String strPivKey = "";
        String strPubKey = "";
        String sign = "089AD594837BFD619676170518B68484744BEE1B53A43B59F144CB01446A3B12DEEC0540F5F3FB828AB2A3AFAD5A1FBADBF4C7DD1B199E9CC6785568ECAAA6E5481E4463B3C9E66DD1C6FFC9539598D83DC6920E7447D1AD965240E9DEF33AE2E6A14B2524533B8ED11AD8EA18A040FE46AA7618F0A8E1C2374C6F5632C15926";
        String normalStr = "";
        String encSign = "";

        sign = "1";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        encSign = encoder.encode(sign);
//
        System.out.println("sign...."+sign);
        System.out.println("encSign...."+encSign);
//        System.out.println("matches...."+encoder.matches(sign, encSign));

        String [] pws = {
//                "dl@xor3rms3"
//                ,"1"
//                ,"lch5329"
//                ,"wndud33^^"
//                ,"milkteatea0628"
//                ,"1"
//                ,"1"
//                ,"3"
//                ,"kk365800"
//                ,"sky4760"
//                ,"stoneistrue"
//                ,"sy905"
//                ,"123123123a!"
//                ,"ikmju0907"
                "hm01"
                ,"1"
                ,"koojihoi"
        };

//        for(String pw: pws){
//            System.out.println("pw....["+pw+":"+encoder.encode(pw)+"]");
//        }
    }
}