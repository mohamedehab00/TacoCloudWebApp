package tacos.controllers;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;
import tacos.repositories.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository) {
		// TODO Auto-generated constructor stub
		this.ingredientRepo = ingredientRepository;
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		Iterable<Ingredient> ingredients = this.ingredientRepo.findAll();

		Type[] types = Ingredient.Type.values();

		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm() {
		return "design";
	}

	private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
		return StreamSupport.stream(ingredients.spliterator(), false).filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
		log.info(taco.toString());
		if (errors.hasErrors()) {
			return "design";
		}
		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);
		return "redirect:/orders/current";
	}
}
