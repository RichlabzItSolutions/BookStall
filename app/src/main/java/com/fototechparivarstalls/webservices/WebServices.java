package com.fototechparivarstalls.webservices;

import com.fototechparivarstalls.model.CancelBooking.CancelBookingJson;
import com.fototechparivarstalls.model.ChangePassword.ChangePasswordJson;
import com.fototechparivarstalls.model.Documents.DocumentsResponse;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.ForgotPaasword.VerifiyOTPJson;
import com.fototechparivarstalls.model.ForgotPaasword.VerifiyOTPResponse;
import com.fototechparivarstalls.model.GetAddons.AddAddonsJson;
import com.fototechparivarstalls.model.GetAddons.GetAddonsResponse;
import com.fototechparivarstalls.model.GetBookings.GetBookingsResponse;
import com.fototechparivarstalls.model.Issue.AddIssueJson;
import com.fototechparivarstalls.model.Issue.DeleteIssueJson;
import com.fototechparivarstalls.model.Issue.EditIssueJson;
import com.fototechparivarstalls.model.Issue.GetIssuesResponse;
import com.fototechparivarstalls.model.Issue.GetIssuessResponse;
import com.fototechparivarstalls.model.Login.GetLoginResponse;
import com.fototechparivarstalls.model.Login.LoginJson;
import com.fototechparivarstalls.model.PastEvents.PastEventsResponse;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryResponse;
import com.fototechparivarstalls.model.ResetPassword.ResetPasswordJson;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebServices {

    @POST("stallUserLogin")
    io.reactivex.Observable<GetLoginResponse>
    getLoginResponse(@Body LoginJson loginJson);

    @POST("forgotPassword")
    io.reactivex.Observable<ForgotPasswordResponse>
    getForgotPasswordResponse(@Body ForgotPasswordJson forgotJson);

    @POST("changePassword")
    io.reactivex.Observable<ForgotPasswordResponse>
    getChangePasswordResponse(@Body ChangePasswordJson changePasswordJson);

    @GET("getUpcomingEvents")
    io.reactivex.Observable<UpcomingEventsResponse>
    getUpcomingEventsListResponse();

    @GET("getPastEvents")
    io.reactivex.Observable<PastEventsResponse>
    getPastEventsListResponse();

    @GET("getBookings")
    io.reactivex.Observable<GetBookingsResponse>
    getBookingListResponse();

    @POST("addIssue")
    io.reactivex.Observable<ForgotPasswordResponse>
    getAddIssueResponse(@Body AddIssueJson addIssueJson);

    @POST("editIssue")
    io.reactivex.Observable<ForgotPasswordResponse>
    getEditIssueResponse(@Body EditIssueJson editIssueJson);

    @POST("deleteIssue")
    io.reactivex.Observable<ForgotPasswordResponse>
    getDeleteIssueResponse(@Body DeleteIssueJson deleteIssueJson);

    @GET("getIssuesList")
    io.reactivex.Observable<GetIssuesResponse>
    getIssuesResponse();

    @POST("getIssues")
    io.reactivex.Observable<GetIssuessResponse>
    getIssuessResponse(@Body PaymentHistoryJson getIssuessJson);

    @POST("cancelBooking")
    io.reactivex.Observable<ForgotPasswordResponse>
    getCancelBookingResponse(@Body CancelBookingJson cancelBookingJson);

    @POST("paymentHistory")
    io.reactivex.Observable<PaymentHistoryResponse>
    getPaymentHistoryResponse(@Body PaymentHistoryJson paymentHistoryJson);

    @POST("getAddons")
    io.reactivex.Observable<GetAddonsResponse>
    getAddonsResponse(@Body PaymentHistoryJson paymentHistoryJson);

    @POST("addAddons")
    io.reactivex.Observable<ForgotPasswordResponse>
    getAddAddonsResponse(@Body AddAddonsJson addAddonsJson);

    @POST("getDocuments")
    io.reactivex.Observable<DocumentsResponse>
    getDocumentsResponse(@Body PaymentHistoryJson documentsJson);


    @POST("resetPassword")
    io.reactivex.Observable<ForgotPasswordResponse>
    getresetPasswordResponse(@Body ResetPasswordJson resetPasswordJson);

    @POST("verifyOTP")
    io.reactivex.Observable<VerifiyOTPResponse>
    getVerifyOtp(@Body VerifiyOTPJson verifyOtpJson);





}
