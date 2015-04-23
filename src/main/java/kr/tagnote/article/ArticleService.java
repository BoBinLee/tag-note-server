package kr.tagnote.article;

import java.security.Principal;
import java.util.List;


import kr.tagnote.tag.Tag;
import kr.tagnote.tag.TagArticle;
import kr.tagnote.tag.TagArticleRepository;
import kr.tagnote.tag.TagRepository;
import kr.tagnote.user.User;
import kr.tagnote.user.UserRepository;
import kr.tagnote.util.CommonUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TagArticleRepository tagArticleRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public void insertArticle(Article.Request request, Principal principal){
		// add Article
		Article article = modelMapper.map(request, Article.class);
		User user = userRepository.findByEmail(principal.getName());
		article.setUserId(user.getUserId());

		articleRepository.save(article);

		// add Tags
		List<String> tags = request.getTags();
		for (int i = 0; tags != null && i < request.getTags().size(); i++) {
			Tag tag = tagRepository.findByName(tags.get(i));

			if (tag == null) {
				tag = new Tag();
				tag.setName(tags.get(i));
				tag.setColor(CommonUtils.getRandomColor());
				tagRepository.save(tag);
			}
			
			// add TagArticles
			TagArticle tagArticle = new TagArticle();
			tagArticle.setArtId(article.getArtId());
			tagArticle.setTagId(tag.getTagId());
			
			tagArticleRepository.save(tagArticle);
		}
	}
	
	public Page<Article.Response> findByPage(Pageable pageRequest){
		List<Article> articles = articleRepository.findAll(pageRequest)
				.getContent();
		List<Article.Response> articleDtos = null;
		Page<Article.Response> pages = null;

		articleDtos = modelMapper.map(articles,
				new TypeToken<List<Article.Response>>() {
				}.getType());
		pages = new PageImpl<Article.Response>(articleDtos);
		
		return pages;
	}
	
}
