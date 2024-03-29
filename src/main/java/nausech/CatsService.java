package nausech;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {
    public static void showCats() throws IOException {
        //get data api
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();
        String jsonReponse = response.body().string();
        //format json
        jsonReponse = jsonReponse.substring(1, jsonReponse.length() - 1);
        Gson gson = new Gson();
        //create cat object
        Cats cats = gson.fromJson(jsonReponse, Cats.class);
        //resize image
        Image image = null;
        ImageIcon catImage = null;
        try {
            URL url = new URL(cats.getUrl());
            image = ImageIO.read(url);
            catImage = new ImageIcon(image);

            if (catImage.getIconWidth() > 800) {
                Image catImageResize = catImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                catImage = new ImageIcon(catImageResize);
            }

        } catch (IOException error) {
            System.out.println(error);
        }

        String menu = "Options: " +
                "1. Show other image cat" +
                "2. Select cat with favorite" +
                "3. bacK \n";
        String[] buttons = {"Show other image", "Select cat with favorite", "back"};
        String idCat = cats.getId();
        String option = (String) JOptionPane.showInputDialog(null, menu, idCat, JOptionPane.INFORMATION_MESSAGE, catImage, buttons, buttons[0]);

        int selection = 1;

        for (int i = 0; i < buttons.length; i++) {
            if (option.equals(buttons[i])) {
                selection = i;
                break;
            }
        }
        switch (selection) {
            case 0:
                showCats();
                break;
            case 1:
                selectFavoriteCat(cats);
            default:
                break;
        }
    }
    public static void selectFavoriteCat(Cats cat) throws IOException {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, STR."{\r\n    \"image_id\":\"\{cat.getId()}\",\r\n    \"sub_id\":\"your-user-123\"\r\n}");
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.thecatapi.com/v1/favourites");
            builder.method("POST", body);
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("x-api-key", cat.getApiKey());
            Request request = builder
                    .build();
            Response response = client.newCall(request).execute();
            // validate successful response
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(null, "Added successful", "Cat api", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException error) {
            System.out.println(STR."Error: \{error}");
        }
    }
    public static void showFavoriteCats() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "live_n70AT1y33uM6WF2roqEKlBsVfpE9ccHn1VNSJxtUz0gX8d5abOjJc10dEYVpnNQo")
                    .build();
            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();
            Gson gson = new Gson();
            Cats[] catsFavs = gson.fromJson(jsonResponse, Cats[].class);
        }catch (IOException error) {
            System.out.println(error);
        }
    }

}
