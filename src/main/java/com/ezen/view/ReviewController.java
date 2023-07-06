package com.ezen.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.ReviewVO;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	/**
	 * Slf4j Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	
	@Autowired
	private ReviewService reviewService;
	
	// ���� ����Ʈ
	@GetMapping("/list")
	
	public Map<String, Object> reciewList(ReviewVO vo) {
		
		Map<String, Object> reviewInfo = new HashMap<>();
				
		List<ReviewVO> reviewList = reviewService.reviewList(vo.getTseq());
		
		reviewInfo.put("total", reviewList.size());
		reviewInfo.put("commentList", reviewList);
		
		// �ΰ��� ���� ���� commentInfo�� �������ش�
		return reviewInfo;
	}
	
	// ���� ���
	@RequestMapping("/save")
	public String saveComment(ReviewVO vo, HttpSession session) {
		// �α����� �Ǿ� �ִ��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		logger.info(vo.toString());
		
		// �α����� �ȵǾ� �ִ� ��� �α��� �������� �̵�
		if (loginUser == null) {
			return "login_form";
			
		} else {
			vo.setId(loginUser.getId());
			reviewService.saveReview(vo);
			logger.info(vo.toString());
			return "success";
		}
		
	}
}