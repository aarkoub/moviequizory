package aarkoub.moviequizory;

import aarkoub.moviequizory.db.user.UserNotFoundException;
import aarkoub.moviequizory.domain.quiz.Quiz;
import aarkoub.moviequizory.domain.quiz.service.IQuizService;
import aarkoub.moviequizory.domain.user.User;
import aarkoub.moviequizory.domain.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@SpringBootApplication
public class MovieQuizoryAPI {

	@Autowired
	IUserService userService;

	@Autowired
	IQuizService quizService;

	@PostMapping("/users/create")
	@ResponseBody
	User createUser(HttpServletRequest request, HttpServletResponse response){
		User u = userService.retrieve(getUserId(request));
		Cookie cookie = new Cookie("userId", u.getId().toString());
		response.addCookie(cookie);
		return u;
	}

	@GetMapping("/users/{id}")
	@ResponseBody
	User getUser(HttpServletRequest request, HttpServletResponse response){
		User u = userService.retrieve(getUserId(request));
		Cookie cookie = new Cookie("userId", u.getId().toString());
		response.addCookie(cookie);
		return u;
	}

	@PostMapping("/users/{id}/highscore/set")
	@ResponseBody
	User setHighscoreUser(@PathVariable String id, @RequestBody Map<String, Integer> score,
						  HttpServletRequest request, HttpServletResponse response){
		try {
			return userService.setHighscore(UUID.fromString(id), score.get("new_highscore"));
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/quizzes/generate")
	@ResponseBody
	Quiz generateQuiz(HttpServletRequest request, HttpServletResponse response){
		return quizService.getQuiz();
	}

	public @Nullable
	UUID getUserId(HttpServletRequest request){
		UUID userId = null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("userId")) {
					userId = UUID.fromString(cookies[i].getValue());
					break;
				}
			}
		}
		return userId;
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieQuizoryAPI.class, args);
	}

}
