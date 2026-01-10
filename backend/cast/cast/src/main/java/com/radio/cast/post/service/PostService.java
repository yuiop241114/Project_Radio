package com.radio.cast.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.radio.cast.post.dto.CursorResponse;
import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.entity.Post;
import com.radio.cast.post.repository.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;
  private static final int Default_size = 20;

  public CursorResponse<PostListResponse> postDate(Long cursor, Integer size) {
     // 21개 조회 (20 + 1)
        List<Post> postList;
        if (cursor == null) {
            postList = postRepository.findTop20ByOrderByPostIdDesc();
        } else {
            postList = postRepository.findTop20ByPostIdLessThanOrderByPostIdDesc(cursor);
        }
        
        // 다음 데이터 존재 여부 확인
        boolean hasNext = postList.size() > Default_size;
        
        // 20개를 조회했으면 마지막 하나 제거
        if (hasNext) {
            postList = postList.subList(0, Default_size);
        }
        
        // DTO 변환
        // List<PostListResponse> content = postList.stream()
        //     .map(PostListResponse::from)
        //     .collect(Collectors.toList());
        List<PostListResponse> contentList = new ArrayList<>();
        for(Post posts : postList){
          contentList.add(new PostListResponse(posts));
        }
        
        // 다음 커서 계산
        Long nextCursor = postList.isEmpty() ? null : postList.get(postList.size() - 1).getPostId();
        
        return new CursorResponse<PostListResponse>(contentList,nextCursor,hasNext,size);
  }
  
}
