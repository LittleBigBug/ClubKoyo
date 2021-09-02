package net.yasfu.clubkoyo.module;

import net.yasfu.clubkoyo.client.ClubModClient;

import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

public class ModuleRegistry {

    private static class RegisteredModule {

        public RegisteredModule(ModuleData data, Module module) {
            this.module = module;
            this.data = data;
        }

        public Module module;
        public ModuleData data;

    }

    public static HashMap<String, RegisteredModule> moduleMap = new HashMap<>();

    public static void initAutoModuleSystem() {
        Reflections reflections = new Reflections();

        Set<Class<?>> moduleClasses = reflections.getTypesAnnotatedWith(ModuleData.class);

        for (Class<?> moduleClass : moduleClasses) {
            String className = moduleClass.getName();

            if (!Module.class.isAssignableFrom(moduleClass)) {
                ClubModClient.getLogger().error("Class {} has ModuleData annotation but doesn't implement Module.", className);
                continue;
            }

            ModuleData[] annotations = moduleClass.getDeclaredAnnotationsByType(ModuleData.class);

            if (annotations.length < 1) {
                ClubModClient.getLogger().error("Class {} reported ModuleData from reflections but doesn't have the annotation..", className);
                continue;
            }

            ModuleData data = annotations[0];
            Module newModule;

            try {
                newModule = (Module) moduleClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Logger logger = ClubModClient.getLogger();

                logger.error("Module {} could not be instantiated", className);
                logger.error(e.getMessage());
                continue;
            }

            addModule(data, newModule);
        }
    }

    public static void addModule(ModuleData data, Module module) {
        RegisteredModule registeredModule = new RegisteredModule(data, module);
        moduleMap.put(data.name(), registeredModule);
    }

    public static void startModules() {
        Logger logger = ClubModClient.getLogger();

        for (RegisteredModule module : moduleMap.values()) {
            ModuleData data = module.data;
            logger.info("Module: {} by {}, starting..", data.name(), data.author());

            module.module.onModuleInit();
        }
    }

}
