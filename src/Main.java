import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(">");

        //Initialise scanner and read input
        Scanner scanner = new Scanner(System.in);
        String input = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

        //Split input by delimiter
        StringTokenizer inputTokens = new StringTokenizer(input, " ,;");

        //Create an array to hold the parsed split input
        ArrayList<Integer> heights = new ArrayList<>();
        try {
            while (inputTokens.hasMoreTokens()) {
                int n = Integer.parseInt(inputTokens.nextToken());

                if (n <= 0 || n >= 10)
                    throw new Exception("Let's keep it in a range between 1 and 10 please");

                heights.add(n);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Calculate water source blocks
        /* water is created if the current block is between to higher blocks no matter the distance between
        calculating the area between two "towers" and subtracting the amount of lower solid blocks.
        water won't be created on the first or last block

        penalty makes the loop keep searching the same index but reducing the height required to create water

        flag if a penalty should be applied only when no block of the same height is found
         */

        int currentHeight, matchingHeightIdx, penalty = 0, waterBlocks = 0;
        boolean flag = false;

        for (int i = 0; i < heights.size(); i++) {
            currentHeight = heights.get(i) - penalty;
            matchingHeightIdx = i;

            if (currentHeight <= 0) //better safe than sorry to prevent infinite loops
                break;

            //search for matching height
            for (int j = i + 1; j < heights.size(); j++) {

                //if one was found store it and break the search
                if (heights.get(j) >= currentHeight) {
                    matchingHeightIdx = j;
                    flag = false;
                    break;
                }

                flag = true;
            }

            //a depression between blocks was found and needs to be bigger than one block apart
            int distance = matchingHeightIdx - i;
            if (distance >= 1) {
                int area = distance * currentHeight;

                //subtract solid blocks
                for (int k = i; k < matchingHeightIdx; k++)
                    if (k == i)
                        area -= heights.get(k) - penalty;
                    else
                        area -= heights.get(k);

                waterBlocks += area;
                penalty = 0; // reset the penalty

                i = matchingHeightIdx - 1; //account to the increment when cycle loops
            }

            if (flag) {
                penalty++;
                i--;
            }

        }

        System.out.println(waterBlocks);
    }
}
