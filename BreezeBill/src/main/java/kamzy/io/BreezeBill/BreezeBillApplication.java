package kamzy.io.BreezeBill;

import kamzy.io.BreezeBill.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BreezeBillApplication {

	public static void main(String[] args) {
		ApplicationContext contex = SpringApplication.run(BreezeBillApplication.class, args);

		UserController authControl = (UserController) contex.getBean("userController");

	}

}
