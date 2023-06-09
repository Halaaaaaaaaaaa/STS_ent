package com.ezen.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.Booking_Total_entVO;
import com.ezen.biz.dto.CsVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.ReviewVO;
import com.ezen.biz.dto.Review_Total_entVO;
import com.ezen.biz.service.BookingService;
import com.ezen.biz.service.CsService;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.ReviewService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private CsService csService;

	// 로그인 화면
	@GetMapping("/login_form")
	public String loginView() {
		return "member/login";
	}

	// 로그인 처리
	@PostMapping("/login")
	public String loginAction(MemberVO vo, Model model) {
		int u_result = memberService.loginID(vo);
		
		if (u_result == 1) {
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			return "redirect:index"; 
		} else if (u_result == 0) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "member/login_fail";
		} else {
			model.addAttribute("errorMessage", "계정이 존재하지 않습니다.");
			return "member/login_fail"; // 로그인 실패시
		}
	}

	// 네이버 로그인 폼
	@GetMapping("/naverlogin")
	public String naverloginf() {

		return "member/naverlogin";
	}

	// 네이버 로그인 처리
	@ResponseBody
	@PostMapping(value = "/naverlogin", produces = "application/text; charset=utf8")
	public String naverloginaction(MemberVO vo, Model model) {

		String message = "";

		MemberVO memberVO = memberService.getMember(vo.getId());
		if (memberVO == null) {
			memberService.insertMember(vo);
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			message = "<script>alert('회원가입 되었습니다.');location.href='index';</script>";

			return message;

		} else {
			model.addAttribute("loginUser", memberService.getMember(memberVO.getId()));
			message = "<script>alert('로그인 되었습니다.');location.href='index';</script>";

			return message;
		}

	}

	// 카카오 로그인 폼
	@GetMapping("/kakaologin")
	public String kakaologinf() {
		return "member/kakaologin";

	}

	// 카카오 로그인 처리
	@ResponseBody
	@PostMapping(value = "/kakaologin", produces = "application/text; charset=utf8")
	public String kakaologinaction(MemberVO vo, Model model) {

		String message = "";

		MemberVO memberVO = memberService.getMember(vo.getId());
		if (memberVO == null) {
			memberService.insertMember(vo);
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			message = "<script>alert('회원가입 되었습니다.');location.href='index';</script>";

			return message;

		} else {
			model.addAttribute("loginUser", memberService.getMember(memberVO.getId()));
			message = "<script>alert('로그인 되었습니다.');location.href='index';</script>";

			return message;
		}

	}
	
	//구글 로그인 폼
	@PostMapping("/googlelogin")
	public String googleloginf() {
		return "member/googlelogin";

	}
	// 회원가입 화면
	@RequestMapping("/signup_form")
	public String signupView() {
		return "member/signup";
	}

	// 회원가입 처리
	@RequestMapping("/signup")
	public String signupAction(MemberVO vo) {

		memberService.insertMember(vo);

		return "redirect:login_form";
	}

	// 이메일 중복 확인
	@ResponseBody
	@PostMapping(value = "/emailcheck", produces = "application/text; charset=utf8")
	public String emailcheck(MemberVO vo) {
		
		MemberVO memberVO = memberService.getMemberEmail(vo);
		if (memberVO == null) {
			return "success";

		} else {//회원이 있을때
			return "fail";
		}

	}
	// id 중복체크
	@RequestMapping(value = "/id_check_form")
	public String idCheckView(MemberVO vo, Model model) {
		int result = memberService.confirmID(vo.getId());

		model.addAttribute("messege", result);
		model.addAttribute("id", vo.getId());

		return "member/idcheck";
	}

	@PostMapping("/id_check_form")
	public String idCheckAString(MemberVO vo, Model model) {

		// id 중복확인 조회 , DAO로 가서 id값을 확인
		int result = memberService.confirmID(vo.getId());

		model.addAttribute("message", result);
		model.addAttribute("id", vo.getId());

		return "member/idcheck";

	}

	// 로그아웃 처리
	@GetMapping("/logout")
	public String logout(SessionStatus status) {

		status.setComplete();
		return "index";
	}

	// 아이디 찾기 창 띄우기
	@RequestMapping("/find_id_form")
	public String findIdFormView() {
		return "member/findIdAndPassword";
	}

	// 아이디 찾기
	@RequestMapping("/find_id")
	public String findIdAction(MemberVO vo, Model model) {
		String id = memberService.selectIdByNameEmail(vo);

		if (id != null) { // 아이디 존재
			model.addAttribute("id", id);
			model.addAttribute("message", 1);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findResult";
	}

	// 비밀번호 찾기
	@RequestMapping("/find_pwd")
	public String findPwdAction(MemberVO vo, Model model) {

		String pwd = memberService.selectPwdByIdNameEmail(vo);

		if (pwd != null) { // 존재하는 ID 경우
			model.addAttribute("id", vo.getId());
			model.addAttribute("message", 1);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findPwdResult";
	}

	// 비번 찾은 후 새 비밀번호 변경
	@PostMapping("/change_pwd")
	public String changePwdAction(MemberVO vo) {

		memberService.changePwd(vo);

		return "member/changePwdOk";
	}

	// my page
	@RequestMapping("/mypage")
	public String mypage(MemberVO vo, Model model, HttpSession session, ReviewVO reviewvo, BookingVO bookingvo) {

		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");

		if (membervo == null) {
			return "member/session_fail";
		}

		reviewvo.setId(membervo.getId());
		bookingvo.setId(membervo.getId());

		// 리뷰 리스트
		List<Review_Total_entVO> list = reviewService.reviewMember(reviewvo);

		// 예약 리스트
		List<Booking_Total_entVO> bookinglist = bookingService.bookingMember(bookingvo);
		List<Booking_Total_entVO> concertList = new ArrayList<>();
		List<Booking_Total_entVO> theaterList = new ArrayList<>();
		List<Booking_Total_entVO> exhibitionList = new ArrayList<>();

		for (Booking_Total_entVO booking : bookinglist) {
			if (booking.getCategory() == 1) {
				concertList.add(booking);
			} else if (booking.getCategory() == 2) {
				theaterList.add(booking);
			} else if (booking.getCategory() == 3) {
				exhibitionList.add(booking);
			} else if (booking.getCategory() == 0) {
				concertList = new ArrayList<>();
				theaterList = new ArrayList<>();
				exhibitionList = new ArrayList<>();
			}
		}
		if (list.isEmpty()) {
			model.addAttribute("noReviewMessage", "작성한 리뷰가 없습니다.");
		}

		model.addAttribute("reviewmemberlist", list);
		model.addAttribute("concertList", concertList);
		model.addAttribute("theaterList", theaterList);
		model.addAttribute("exhibitionList", exhibitionList);
		model.addAttribute("membervo", membervo);

		return "member/mypage";
	}

	// my page 수정
	@RequestMapping(value = "/mypage_updateF")
	public String updateMemberF(Model model, HttpSession session) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");

		model.addAttribute("membervo", membervo);

		return "member/mypage_updateF";
	}

	// my page 수정 처리
	@RequestMapping("/mypage_update")
	public String updateMember(MemberVO vo, HttpSession session, Model model) {

		memberService.updateMember(vo);

		model.addAttribute("loginUser", memberService.getMember(vo.getId()));
		model.addAttribute("membervo", vo);
		return "redirect:mypage";
	}

	// my page - 회원 탈퇴 처리
	@ResponseBody
	@PostMapping(value = "/member_delete", produces = "application/text; charset=utf8")
	public String member_delete(MemberVO vo, HttpSession session, SessionStatus status) throws Exception {

		try {

			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			String message = "";
			if (loginUser.getPassword().equals(vo.getPassword())) {

				memberService.deleteMember(loginUser.getId());
				bookingService.deleteBookingfromid(loginUser.getId());
				status.setComplete();
				message = "<script>alert('삭제되었습니다. 저희 서비스를 이용해주셔서 감사했습니다.');location.href='index';</script>";

				return message;
			} else {

				return "fail";
			}

		} catch (NullPointerException e) {
			return "<script>alert('로그인 후 이용해주세요.');location.href='login_form';</script>";
		}
	}

	// 리뷰 삭제
	@RequestMapping(value = "/reviewDelete", method = RequestMethod.GET)
	public String reviewDelete(HttpSession session, @RequestParam("rseq") int rseq, ReviewVO reviewVO) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		reviewVO.setId(loginUser.getId());
		reviewVO.setRseq(rseq);

		reviewService.deleteReview(reviewVO.getRseq());

		return "redirect:mypage";
	}

	// 예약 삭제
	@RequestMapping(value = "/reservationDelete", method = RequestMethod.GET)
	public String reservationDelete(HttpSession session, BookingVO bookingvo, @RequestParam("bseq") int bseq) {

		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		bookingvo.setBseq(bseq);
		bookingvo.setId(loginUser.getId());

		bookingService.deleteBooking(bookingvo);

		return "redirect:mypage";
	}

	// footer- about us
	@GetMapping("/aboutus")
	public String aboutus() {
		return "aboutus";
	}

	// footer- contact us
	@GetMapping("/contactus")
	public String contactus() {
		return "contactus";
	}

	// footer- privacy policy
	@GetMapping("/privacy_policy")
	public String privacy_policy() {
		return "privacy_policy";
	}

	//세션만료
	@GetMapping("/session_fail")
	public String session_fail() {
		return "member/session_fail";
	}
	
	//문의사항 페이지
	@GetMapping("/cs_insertF")
	public String cs_insertForm(HttpSession session, MemberVO member, Model model, CsVO csvo) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
			
		if (membervo == null) {
			return "member/session_fail";
		} 
		
		csvo.setId(membervo.getId());
		csvo.setName(membervo.getName());
		csvo.setEmail(membervo.getEmail());
			
		model.addAttribute("member", csvo);
		return "cs/cs_insertF";
	}
			
	//문의사항 insert
	@PostMapping(value="/cs_insert")
	public String cs_insertAction(CsVO csvo, HttpSession session) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
			
		if (membervo == null) {
			return "member/session_fail";
		} 
			
		csvo.setCstatus("답변대기");
		csService.insertCS(csvo);
		
		return "redirect:mypage";
	}
		
	//문의사항 list
	@GetMapping("/cs_list")
	public String cs_list(CsVO csvo, Model model, HttpSession session) {
		
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
		
		if (membervo == null) {
			return "member/session_fail";
		} 
		
		List<CsVO> csList = csService.csList(membervo.getId());
		
		model.addAttribute("csList", csList);
		return "cs/cs_list";
	}
	
	//문의사항 detail
	@GetMapping("/cs_detail")
	public String cs_detail(HttpSession session, CsVO csvo, Model model) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
		
		if (membervo == null) {
			return "member/session_fail";
		} 
		
		CsVO cs = csService.csDetail(csvo.getCseq());
		
		model.addAttribute("cs", cs);
		
		return "cs/cs_detail";
	}
	
	//문의사항 수정 form
	@GetMapping("/cs_updateF")
	public String cs_updateF(HttpSession session, CsVO csvo, Model model) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
		
		if (membervo == null) {
			return "member/session_fail";
		} 
		
		CsVO cs = csService.csDetail(csvo.getCseq());
		
		model.addAttribute("cs", cs);
		
		return "cs/cs_updateF";
	}
	
	//문의사항 수정 action
	@PostMapping("/cs_update")
	public String cs_update(HttpSession session, CsVO csvo, Model model) {
		MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
		
		if (membervo == null) {
			return "member/session_fail";
		} 
		
		csService.csUpdate(csvo);
		
		return "redirect:cs_detail?cseq=" + csvo.getCseq();
	}
	
	//문의사항 삭제
	@ResponseBody
	@PostMapping(value = "/cs_delete", produces = "application/text; charset=utf8")
	public String cs_delete(CsVO csvo, HttpSession session, MemberVO vo) {

		try {
			MemberVO membervo = (MemberVO) session.getAttribute("loginUser");
			
			if (membervo == null) {
				return "member/session_fail";
			} 

			String message = "";
			if (membervo.getPassword().equals(vo.getPassword())) {
				csService.csDelete(csvo);
				message = "<script>alert('삭제되었습니다.');location.href='mypage';</script>";
				return message;
			} else {
				return "fail";
			}

		} catch (NullPointerException e) {
			return "<script>alert('로그인 후 이용해주세요.');location.href='login_form';</script>";
		}
	}

}