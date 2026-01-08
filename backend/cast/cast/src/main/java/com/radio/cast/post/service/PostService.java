package com.radio.cast.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.radio.cast.post.dto.CursorResponse;
import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.entity.Post;
import com.radio.cast.post.repository.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;
  private static final int Default_size = 20;

  public CursorResponse<PostListResponse> postDate(Long cursor, Integer size) {
     // size가 없으면 기본값 사용
        int pageSize = (size == null) ? DEFAULT_SIZE : size;

        // size + 1개를 조회해서 다음 데이터 존재 여부 확인
        List<Post> posts;
        if (cursor == null) {
            // 첫 조회
            posts = postRepository.findTopNByOrderByIdDesc(pageSize + 1);
        } else {
            // cursor 이후 조회
            posts = postRepository.findTopNByIdLessThanOrderByIdDesc(
                cursor,
                pageSize + 1
            );
        }

        // 다음 데이터 존재 여부 확인
        boolean hasNext = posts.size() > pageSize;

        // size + 1개를 조회했으면 마지막 하나 제거
        if (hasNext) {
            posts = posts.subList(0, pageSize);
        }

        // DTO 변환
        List<PostDto> content = posts.stream()
            .map(PostDto::from)
            .collect(Collectors.toList());

        // 다음 커서 계산 (마지막 데이터의 ID)
        Long nextCursor = posts.isEmpty() ? null : posts.get(posts.size() - 1).getId();

        return CursorResponse.<PostDto>builder()
            .content(content)
            .nextCursor(nextCursor)
            .hasNext(hasNext)
            .size(content.size())
            .build();
    }
  }
  
}
