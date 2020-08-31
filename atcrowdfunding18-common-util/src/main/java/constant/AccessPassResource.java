package constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 孔佳齐丶
 * @create 2020-08-29 13:20
 * @package constant
 */
public class AccessPassResource {

    public static final Set<String> PASS_RESULT_SET = new HashSet<>();

    static {
        PASS_RESULT_SET.add("/");
        PASS_RESULT_SET.add("/auth/member/to/reg/page");
        PASS_RESULT_SET.add("/auth/member/to/login/page");
        PASS_RESULT_SET.add("/auth/member/loginOut");
        PASS_RESULT_SET.add("auth/member/do/login");
        PASS_RESULT_SET.add("auth/member/sent/short/message.json");
        PASS_RESULT_SET.add("/auth/do/member/register");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 用于判断是否为静态资源
     * @param servletPath
     * @return true:是静态资源
     *          false: 不是静态资源
     */
    public static boolean judgeCurrentServletPathStaticResource(String servletPath){

        if(servletPath==null||servletPath.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        //2.根据""/ 拆分ServletPath字符串
        String[] split = servletPath.split("/");

        //3.考虑第一个斜杠左边经过分析得到一个空字符串是数组的第一个元素,所以要使用下标为1的元素
        String firstLevelPath = split[1];

        //判断是否在集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }

    /*public static void main(String[] args) {
        String servletPath="/bootstrap/bbb/cc";
        boolean b = judgeCurrentServletPathStaticResource(servletPath);
        System.out.println(b);
    }*/

}
