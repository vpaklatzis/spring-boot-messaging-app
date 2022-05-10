package io.javabrains;

import io.javabrains.inbox.DataStaxAstraProperties;
import io.javabrains.inbox.folders.Folder;
import io.javabrains.inbox.folders.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

@SpringBootApplication
public class MessagingAppApplication {
	@Autowired
	private FolderRepository folderRepository;

	public static void main(String[] args) {
		SpringApplication.run(MessagingAppApplication.class, args);
	}
	@Bean
	public CqlSessionBuilderCustomizer cqlSessionBuilderCustomizer(DataStaxAstraProperties dataStaxAstraProperties) {
		Path bundle = dataStaxAstraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void init() {
		folderRepository.save(new Folder("test_user_1", "inbox", "red"));
		folderRepository.save(new Folder("test_user_2", "sent", "yellow"));
		folderRepository.save(new Folder("test_user_3", "work", "green"));
	}
}
