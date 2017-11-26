package proyekpakdani.abcd;

/**
 * Created by SLD on 06-Sep-17.
 */

import java.util.List;

import okhttp3.ResponseBody;
import proyekpakdani.abcd.models.Isi;
import proyekpakdani.abcd.models.QuestionnaireContent;
import proyekpakdani.abcd.models.PostRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Interface {

    @GET Call<List<Isi>> getProjects(@Url String url);

    @GET Call<List<QuestionnaireContent>> getQuestionnaire(@Url String url);

    @POST("/login") Call<ResponseBody> postLogin(@Body PostRequest postRequest);
}
