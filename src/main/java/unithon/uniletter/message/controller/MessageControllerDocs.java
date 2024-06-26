package unithon.uniletter.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import unithon.uniletter.common.error.ErrorResponse;
import unithon.uniletter.member.Member;
import unithon.uniletter.message.dto.MessageListResponse;
import unithon.uniletter.message.dto.MessageRequest;
import unithon.uniletter.message.dto.MessageResponse;
import unithon.uniletter.message.dto.UnreadMessageResponse;

import java.util.UUID;

public interface MessageControllerDocs {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "SERVER ERROR",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "메시지 전송",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageRequest.class),
                            examples = {
                                    @ExampleObject(name = "MessageRequestExample", value = """
                                            {
                                            \t"receiverNickname": "nick123",
                                            \t"senderName": "보내는 닉네임",
                                            \t"eventId": "(있는 경우만) 대상 event id",
                                            \t"content": "보낼 메시지",
                                            \t"type": "메시지 타입",
                                            \t"sendPlannedAt": "2024-04-28"
                                            }""")
                            })))
    ResponseEntity<Void> sendMessage(@Valid @RequestBody final MessageRequest request,
                                     @Parameter(hidden = true) final Member member);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "500", description = "SERVER ERROR",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "읽지 않은 메시지 리스트 조회")
    ResponseEntity<UnreadMessageResponse> getUnreadMessages(
            @Parameter(hidden = true) final Member member);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "500", description = "SERVER ERROR",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "받은 전체 메시지 조회")
    ResponseEntity<MessageListResponse> getAllMessages(@Parameter(hidden = true) final Member member);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "SERVER ERROR",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "단일 메시지 조회")
    ResponseEntity<MessageResponse> getSingleMessage(
            @Parameter(description = "id of message", example = "ete-dfdfd-fdfder", required = true)
            @PathVariable UUID id,
            @Parameter(hidden = true) final Member member);
}
