package com.passm.controller.confirmation;

import java.util.logging.Logger;

import com.passm.controller.ConsoleController;
import com.passm.controller.Recipient;
import com.passm.model.config.Configuration;
import com.passm.view.confirmation.ConfirmationView;

public class ConfirmationController extends ConsoleController<ConfirmationController, ConfirmationView> {
	
	private final static Logger LOGGER = Logger.getLogger(ConfirmationController.class.getName());
	
	private Recipient recipient;
	
	public ConfirmationController(ConsoleController<?, ?> previousController, ConfirmationView confirmationView,
			Configuration configuration, Recipient recipient) {
		super(previousController, confirmationView, configuration);
		this.recipient = recipient;
	}

	@Override
	public ConfirmationController getInstance() {
		return this;
	}
	
	public void returnResponce(boolean confirmed) {
		LOGGER.info("Confirmation: " + confirmed);
		recipient.receive(confirmed);
	}

}
