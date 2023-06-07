import HTTP.HTTPToken;

public class ExampleExecution {

    public static void main(String[] args) {

        HTTPToken httpToken = HTTPToken.getInstance();
        System.out.println("write token " + httpToken.getWriteToken());
        System.out.println("read token " + httpToken.getReadToken());
        System.out.println("write token " + httpToken.getWriteToken());
        System.out.println("read token " + httpToken.getReadToken());
        System.out.println("write token " + httpToken.getWriteToken());
        System.out.println("read token " + httpToken.getReadToken());
        System.out.println("write token " + httpToken.getWriteToken());
        System.out.println("read token " + httpToken.getReadToken());

        System.out.println("!!!!!!!!!!!!! ");
        HTTPToken httpToken22 = HTTPToken.getInstance();
        System.out.println("write token " + httpToken22.getWriteToken());
        System.out.println("read token " + httpToken22.getReadToken());
        System.out.println("write token " + httpToken22.getWriteToken());
        System.out.println("read token " + httpToken22.getReadToken());
        System.out.println("write token " + httpToken22.getWriteToken());
        System.out.println("read token " + httpToken22.getReadToken());
        System.out.println("write token " + httpToken22.getWriteToken());
        System.out.println("read token " + httpToken22.getReadToken());

    }
}
