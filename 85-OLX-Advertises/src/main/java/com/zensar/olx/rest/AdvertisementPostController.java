package com.zensar.olx.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.bean.AdvertisementPost;
import com.zensar.olx.bean.AdvertisementStatus;
import com.zensar.olx.bean.Category;
import com.zensar.olx.bean.NewAdvertisementPostRequest;
import com.zensar.olx.bean.NewAdvertisementPostResponse;
import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.service.AdvertisementPostService;

@RestController
public class AdvertisementPostController {

	@Autowired
	AdvertisementPostService service;

	@PostMapping("/advertise/{un}")
	public NewAdvertisementPostResponse add(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {
		AdvertisementPost post = new AdvertisementPost();
		post.setTitle(request.getTitle());
		post.setPrice(request.getPrice());
		post.setDescription(request.getDescription());

		int categoryId = request.getCategoryId();

		RestTemplate restTemplate = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getCategory/" + categoryId;
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		// System.out.println(olxUser);
		post.setOlxUser(olxUser);

		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "OPEN");
		post.setAdvertismentStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.addAdvertisement(post);

		NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
		response.setId(advertisementPost.getId());
		response.setTitle(advertisementPost.getTitle());
		response.setPrice(advertisementPost.getPrice());
		response.setCategory(advertisementPost.getCategory().getName());
		response.setDescription(advertisementPost.getDescription());
		response.setUserName(advertisementPost.getOlxUser().getUserName());
		response.setCreatedDate(advertisementPost.getCreatedDate());
		response.setModifiedDate(advertisementPost.getModifiedDate());
		response.setStatus(advertisementPost.getAdvertismentStatus().getStatus());
		return response;
	}

	@PutMapping("/advertise/{aid}/{userName}") // update the record details using advertise id

	public NewAdvertisementPostResponse update(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "aid") int id, @PathVariable(name = "userName") String userName) {
		AdvertisementPost post = this.service.getAdvertisementById(id);
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPrice(request.getPrice());

		RestTemplate restTemplate = new RestTemplate();

		Category category;
		String url = "http://localhost:9052/advertise/getCategory/" + request.getCategoryId();
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		url = "http://localhost:9052/advertise/status/" + request.getStatusId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		post.setAdvertismentStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.updateAdvertisement(post);

		NewAdvertisementPostResponse postResponse;
		postResponse = new NewAdvertisementPostResponse();

		postResponse.setId(advertisementPost.getId());
		postResponse.setTitle(advertisementPost.getTitle());
		postResponse.setDescription(advertisementPost.getDescription());
		postResponse.setPrice(advertisementPost.getPrice());
		postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
		postResponse.setCategory(advertisementPost.getCategory().getName());
		postResponse.setCreatedDate(advertisementPost.getCreatedDate());
		postResponse.setModifiedDate(advertisementPost.getModifiedDate());
		postResponse.setStatus(advertisementPost.getAdvertismentStatus().getStatus());

		return postResponse;
	}

	@GetMapping("/user/advertise/{userName}") // get the all the records by name (/Sathish we can get the all Sathish
												// records in the advertise)
	public List<NewAdvertisementPostResponse> f3(@PathVariable(name = "userName") String userName) {
		List<AdvertisementPost> advPost = this.service.getAllAdvertisements();
		RestTemplate restTemplate = new RestTemplate();
		List<AdvertisementPost> filterList = new ArrayList<>();
		String url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);

		for (AdvertisementPost post : advPost) {

			Category category;
			url = "http://localhost:9052/advertise/getCategory/" + post.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			post.setCategory(category);
			// System.out.println("Category-------" + post);

			url = "http://localhost:9052/advertise/status/" + post.getAdvertismentStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			post.setAdvertismentStatus(advertisementStatus);

			// System.out.println("AdvertisementStatus" + post);

			if (olxUser.getOLXUserId() == post.getOlxUser().getOLXUserId()) {
				post.setOlxUser(olxUser);
				filterList.add(post);
			}
		}

		// System.out.println("List--------------"+filterList);

		List<NewAdvertisementPostResponse> responseList = new ArrayList<>();
		for (AdvertisementPost advertisementPost : filterList) {
			NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
			postRespone.setId(advertisementPost.getId());
			postRespone.setTitle(advertisementPost.getTitle());
			postRespone.setDescription(advertisementPost.getDescription());
			postRespone.setPrice(advertisementPost.getPrice());
			postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
			postRespone.setCategory(advertisementPost.getCategory().getName());
			postRespone.setCreatedDate(advertisementPost.getCreatedDate());
			postRespone.setModifiedDate(advertisementPost.getModifiedDate());
			postRespone.setStatus(advertisementPost.getAdvertismentStatus().getStatus());

			responseList.add(postRespone);
		}
		return responseList;
	}

	@GetMapping("/user/advertise/{advertiseId}/{userName}") // get the particular record details using advertise id

	public List<NewAdvertisementPostResponse> getAllAdvertisementsById(@PathVariable(name = "advertiseId") int id,
			@PathVariable(name = "userName") String userName) {

		List<NewAdvertisementPostResponse> allResponses = new ArrayList<>();
		List<AdvertisementPost> allPosts = this.service.getAllAdvertisements();

		RestTemplate restTemplate = new RestTemplate();
		String url = null;

		for (AdvertisementPost advertisementPost : allPosts) {

			url = "http://localhost:9051/user/find/" + userName;
			OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);

			if (olxUser.getUserName().equals(userName)) {

				if (advertisementPost.getId() == id) {
					NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
					response.setId(advertisementPost.getId());
					response.setTitle(advertisementPost.getTitle());
					response.setDescription(advertisementPost.getDescription());
					response.setPrice(advertisementPost.getPrice());

					advertisementPost.setOlxUser(olxUser);
					response.setUserName(advertisementPost.getOlxUser().getUserName());
					Category category;
					url = "http://localhost:9052/advertise/getCategory/" + advertisementPost.getCategory().getId();
					category = restTemplate.getForObject(url, Category.class);
					advertisementPost.setCategory(category);
					response.setCategory(advertisementPost.getCategory().getName());
					response.setCreatedDate(advertisementPost.getCreatedDate());
					response.setModifiedDate(advertisementPost.getModifiedDate());

					url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertismentStatus().getId();
					AdvertisementStatus advertisementStatus;
					advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
					advertisementPost.setAdvertismentStatus(advertisementStatus);
					response.setStatus(advertisementPost.getAdvertismentStatus().getStatus());
					allResponses.add(response);
				}
			}
		}
		return allResponses;
	}

	@DeleteMapping("/user/advertise/{advertiseId}/{userName}")
	public boolean delteAdvertisementById(@PathVariable(name = "advertiseId") int id,
			@PathVariable(name = "userName") String userName) {

		boolean result = false;

		// AdvertisementPost advertisementPost= this.service.getAdvertisementById(id);

		List<AdvertisementPost> allPosts = this.service.getAllAdvertisements();

		RestTemplate restTemplate = new RestTemplate();
		String url = null;

		for (AdvertisementPost advertisementPost : allPosts) {

			url = "http://localhost:9051/user/find/" + userName;
			OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
			if (olxUser.getUserName().equals(userName)) {

				if (advertisementPost.getId() == id) {

					result = this.service.deleteAdvertisementPost(advertisementPost);

				}
			}
		}
		return result;
	}
}