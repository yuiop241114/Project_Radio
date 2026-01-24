package com.radio.cast.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.radio.cast.post.dto.CursorResponse;
import com.radio.cast.post.dto.PostDetailResponse;
import com.radio.cast.post.dto.PostEditRequest;
import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.dto.PostWriteRequest;
import com.radio.cast.post.entity.Post;
import com.radio.cast.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private static final int Default_size = 10;

  /**
   * 무한 스크롤 Cursor 서비스
   * @param cursor
   * @param size
   * @return
   */
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

  public PostDetailResponse postDetail(Long postId){
    return new PostDetailResponse(postRepository.findByPostId(postId).get());
  }
  
  /**
   * 게시글 수정 서비스
   * @param editData
   * @return
   */
  @Transactional
  public PostDetailResponse postEdit(PostEditRequest editData){
    Post post = postRepository.findByPostId(editData.getPostId()).get();
    // post.setPostTitle(editData.getPostTitle());
    // post.setPostContent(editData.getPostContent());
    post.update(editData.getPostTitle(), editData.getPostContent());
    return new PostDetailResponse(post);
  }

  /**
   * 게시글 생성 서비스
   * @param writeData
   * @return
   */
  @Transactional
  public PostWriteRequest postWrite(PostWriteRequest writeData){
    Post writePost = new Post(writeData.getPostTitle(), writeData.getPostContent(), writeData.getPostAuthor());
    return new PostWriteRequest(postRepository.save(writePost));
  }

  /**
   * 게시글 삭제 서비스
   * @param postId
   */
  @Transactional
  public void postDelete(Long postId){
    Post post = postRepository.findByPostId(postId).get();
    postRepository.delete(post);
  }
}
