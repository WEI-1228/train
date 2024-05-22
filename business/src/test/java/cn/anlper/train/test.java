package cn.anlper.train;

public class test {
    public static void main(String[] args) {
        char[] charArray = "110011100".toCharArray();
        int startIndex = 5;
        int endIndex = 8;
        int minStart = startIndex;
        while (minStart > 1 && charArray[minStart - 2] == '0') {
            minStart--;
        }
        int maxEnd = endIndex;
        while (maxEnd <= charArray.length && charArray[maxEnd - 1] == '0') {
            maxEnd++;
        }
        int maxStart = endIndex - 1;
        int minEnd = startIndex + 1;
        System.out.println("影响出发站的区间：" + minStart + ", " + maxStart);
        System.out.println("影响到达站的区间：" + minEnd + ", " + maxEnd);
    }
}
