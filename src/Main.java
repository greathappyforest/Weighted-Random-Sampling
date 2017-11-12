import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("red");
        list.add("yellow");
        list.add("red");
        list.add("yellow");
        list.add("red");
        list.add("blue");
        list.add("yellow");
        list.add("red");
        list.add("yellow");
        list.add("yellow");

        List<Pair> res = WeightedRandomSamplingProbability(list, list.size());
        for (Pair item : res) {
            System.out.println(item.getKey() + ": "+ item.getValue());
        }

        System.out.println("=============================================");

        List<Pair> itemsWeight= new ArrayList<Pair>();
        itemsWeight.add(new Pair("red", 0.4));
        itemsWeight.add(new Pair("yellow", 0.5));
        itemsWeight.add(new Pair("blue", 0.1));
        List<String> res2 = WeightedRandomSamplingSequence(itemsWeight,10000);

        HashMap<String, Integer> map =new HashMap<String, Integer>();
        for (String item : res2) {
            if (map.containsKey(item)) {
                int value = map.get(item);
                map.put(item, value + 1);
            } else {
                map.put(item, 1);
            }
            System.out.print(item+ " ");
        }
        System.out.println("=============================================");
        System.out.println("Summary:");
        for (Map.Entry<String, Integer> map2 : map.entrySet()) {
            String key = map2.getKey();
            Integer value = map2.getValue();
            System.out.println(key + ": " + value);
        }
    }



    // input: list of items (eg: different color of balls), and size of balls
    // output: list of Pair contains probability of each group of color balls within all sample balls
    public static List<Pair> WeightedRandomSamplingProbability(List<String> samples, Integer numOfTotalSamples) {
        List<Pair> res = new ArrayList<Pair>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String item : samples) {
            if (map.containsKey(item)) {
                int value = map.get(item);
                map.put(item, value + 1);
            } else {
                map.put(item, 1);
            }
        }

        for (Map.Entry<String, Integer> group : map.entrySet()) {
            String key = group.getKey();
            Integer value = group.getValue();
            res.add(new Pair(key , (double) value / numOfTotalSamples));
        }
        return res;
    }

    // Input: Samples in list of Pair contains probability of each group of color balls within all sample balls (sum of total Probability must be 1)
    // Generate list of items follow each probability
    public static List<String>  WeightedRandomSamplingSequence(List<Pair> samples, int n) {
        List<String> res2= new ArrayList<String>();
        double tmp=0;
        //red:0.4, yellow:0.9, blue: 1.0
        for(Pair pair: samples){
            tmp+=pair.getValue();
            pair.setValue(tmp);
        }
        //check in interval of probability within 1.
        for(int i=0; i<n; i++){
            double seed = Math.random();
            // because probablity is incremental, so only need to check seed<pair.getvalue(),if yes, jump current loop,
            // keep generator a new seed.
            for(Pair pair: samples){
                if(seed<=pair.getValue()){
                    res2.add(pair.getKey());
                    break;
                }
            }

        }
        return res2;
    }


    public static class Pair
    {
        private String key;
        private Double value;

        public Pair(String Key, Double Value)
        {
            this.key   = Key;
            this.value = Value;
        }


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }

}
