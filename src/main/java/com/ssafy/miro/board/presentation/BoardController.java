package com.ssafy.miro.board.presentation;

import com.ssafy.miro.board.application.BoardService;
import com.ssafy.miro.board.application.response.BoardItems;
import com.ssafy.miro.board.presentation.request.BoardRequest;
import com.ssafy.miro.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.ssafy.miro.common.code.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createBoard(@Valid @RequestBody BoardRequest boardRequest) {
        Long newBoardId = boardService.save(boardRequest);
        return ResponseEntity.created( URI.create("/board/"+newBoardId)).body(ApiResponse.of(CREATE_BOARD, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardItems>>> getBoards() {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(boardService.getBoards()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardItems>> getBoardById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(boardService.getBoard(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        boardService.updateBoard(id, boardRequest);
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_BOARD, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_BOARD, null));
    }

}
