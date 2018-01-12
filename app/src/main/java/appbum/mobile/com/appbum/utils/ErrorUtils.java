package appbum.mobile.com.appbum.utils;


import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import appbum.mobile.com.appbum.api.models.ErrorServiceModel;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorUtils {

    private static final String CONNECTION_ERROR = "Revisa tu conexión a internet.";

    public static String getMessageError(Throwable e) {
        e.printStackTrace();
        String error = "Server Error";
        try {
            error = e.getMessage();
            if (e instanceof HttpException) {

                ResponseBody responseBody = ((HttpException) e).response().errorBody();

                ErrorServiceModel defaultError;

                defaultError = new Gson().fromJson(responseBody.string(), ErrorServiceModel.class);

                error = TextUtils.isEmpty(defaultError.getError()) ? defaultError.getMessage() : defaultError.getError();
            } else if (e instanceof IOException) {
                error = CONNECTION_ERROR;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return error;
    }

    public static ErrorServiceModel getError(Throwable e) {
        ErrorServiceModel error = null;
        try {
            if (e instanceof HttpException) {
                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                ErrorServiceModel defaultError;
                defaultError = new Gson().fromJson(responseBody.string(), ErrorServiceModel.class);
                error = defaultError;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return error;
    }

}
