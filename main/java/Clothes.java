import java.util.HashMap;

class Clothes {
    static HashMap<Integer,String> clothe = new HashMap<Integer, String>();
    static HashMap<Integer,String> clotheRain = new HashMap<Integer, String>();
    static HashMap<Integer,String> clotheB = new HashMap<Integer, String>();
    Clothes() {
        clothe.put(4, "https://sun9-21.userapi.com/c857036/v857036197/784c/xx4PfAX3VcE.jpg");
        clothe.put(3, "https://sun9-25.userapi.com/c857036/v857036197/7843/Bjetvod9uag.jpg");
        clothe.put(2, "https://sun9-11.userapi.com/c857036/v857036197/783a/K2WxOmxakjA.jpg");
        clothe.put(1, "https://sun9-12.userapi.com/c857036/v857036197/7831/D32Ty1j-ThU.jpg");
        clothe.put(0,"https://sun9-34.userapi.com/c857036/v857036197/7855/t-Q6Naw06IA.jpg");
        clothe.put(-1,"https://sun9-69.userapi.com/c857036/v857036197/785e/HCUwedjlQsA.jpg");
        clothe.put(-2,"https://sun9-34.userapi.com/c857036/v857036197/7867/TpG5vuguzAQ.jpg");
        clothe.put(-3, "https://sun9-39.userapi.com/c857036/v857036197/7870/De2gRZqp13s.jpg");
        clothe.put(-4, "https://sun9-57.userapi.com/c857036/v857036197/7879/QtDN1YM4SLM.jpg");
        clothe.put(-5, "https://sun9-47.userapi.com/c857036/v857036197/7882/2rPeRbxavcU.jpg");
        clotheRain.put(4,"https://sun9-53.userapi.com/c857036/v857036197/78cc/Rd7XimRdx98.jpg");
        clotheRain.put(3,"https://sun9-36.userapi.com/c857036/v857036197/78c3/bqoOUlP2FiA.jpg");
        clotheRain.put(2,"https://sun9-32.userapi.com/c857036/v857036197/78ba/ccP54Lp-2wo.jpg");
        clotheRain.put(1,"https://sun9-50.userapi.com/c857036/v857036197/78b1/A5LFhY4ob-M.jpg");
        clotheRain.put(0,"https://sun9-19.userapi.com/c857036/v857036197/78d5/0F3AIHiPIYY.jpg");
        clotheB.put(3, "https://sun9-20.userapi.com/c857036/v857036197/7903/Cl5ah0GzVEg.jpg");
        clotheB.put(2,"https://sun9-71.userapi.com/c857036/v857036197/78fa/XKGYQEZigeU.jpg");
        clotheB.put(1,"https://sun9-41.userapi.com/c857036/v857036197/78f1/zuWzwT2Iq3U.jpg");
        clotheB.put(0,"https://sun9-58.userapi.com/c857036/v857036197/790c/-SO9o9i8f4U.jpg");



    }

    static String getIm(int id, double wind, double temp)
    {
        if (wind > 5)
            temp = temp - 5;
        int t = (int)temp / 5;
        if (t < -5)
        {
            t = -5;
        }
        if (t > 4){
            t = 4;
        }
        if (id >=200 && id <= 500)
        {
            return clotheRain.get(t);
        }
        else if (id >= 700 && id < 800)
        {return clotheB.get(t);}
        else
        {
            return clothe.get(t);}
    }
}
