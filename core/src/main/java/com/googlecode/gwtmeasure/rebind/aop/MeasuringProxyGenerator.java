package com.googlecode.gwtmeasure.rebind.aop;


import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

import java.io.PrintWriter;

/**
 *
 * @author dmitry.buzdin
 */
public class MeasuringProxyGenerator extends Generator {

    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String className) throws UnableToCompleteException {
        TypeOracle typeOracle = context.getTypeOracle();
        JClassType measuredClass = typeOracle.findType(className);

        if (measuredClass.isFinal() || measuredClass.isAbstract()
                || (measuredClass.isInterface() != null)) {
            logger.log(TreeLogger.Type.ERROR,
                    "Measurement of final/abstract classes and interfaces is not supported : " + className);
        }

        JPackage aPackage = measuredClass.getPackage();
        String packageName = aPackage.getName();

        String name = measuredClass.getSimpleSourceName();
        String proxyName = name + "_Proxy";

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, proxyName);

        composerFactory.addImport(Measurements.class.getCanonicalName());
        composerFactory.addImport(OpenMeasurement.class.getCanonicalName());
        composerFactory.addImport(Constants.class.getCanonicalName());

        composerFactory.setSuperclass(className);

        PrintWriter printWriter = context.tryCreate(logger, packageName, proxyName);

        SourceWriter sw = composerFactory.createSourceWriter(context, printWriter);
        sw.indent();


        JMethod[] methods = measuredClass.getMethods();
        for (JMethod method : methods) {
            if (!method.isPublic() || method.isFinal()) {
                continue;
            }

            String returnType = generateReturnType(method);

            sw.println("@Override");
            JParameter[] parameters = method.getParameters();
            sw.println("public " + returnType + " " + method.getName()
                    + "(" + generateParameterSignature(parameters) + ") "
                    + generateThrowsSignature(method) + " {");
            sw.indent();
            sw.println("OpenMeasurement m = Measurements.start(\"" + name + "." + method.getName() + "\");");
            sw.println("m.setParameter(Constants.PARAM_METHOD, \"" + name + "." + method.getName() + "\");");
            sw.println("try {");
            sw.indent();
            if (!"void".equals(returnType)) {
                sw.println(returnType + " result;");
                sw.print("result = ");
            }
            sw.println("super." + method.getName() + "(" + generateParameters(parameters) + ");");
            sw.println("m.stop();");
            if (!"void".equals(returnType)) {
                sw.println("return result;");
            }
            sw.outdent();
            sw.println("} catch(RuntimeException e) {");
            sw.indent();
            sw.println("m.discard();");
            sw.println("throw e;");
            sw.outdent();
            sw.println("}");
            sw.outdent();
            sw.println("}");
            sw.println();
        }

        sw.commit(logger);
        return packageName + "." + proxyName;
    }

    private String generateReturnType(JMethod method) {
        JType returnType = method.getReturnType();

        if (returnType.isTypeParameter() != null) {
            JTypeParameter genericType = returnType.isTypeParameter();
            return genericType.getBaseType().getSimpleSourceName();
        }

        return returnType.getQualifiedSourceName();
    }

    private String generateThrowsSignature(JMethod method) {
        JType[] methodThrows = method.getThrows();
        if (methodThrows.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder("throws ");
        for (int i = 0, methodThrowsLength = methodThrows.length; i < methodThrowsLength; i++) {
            JType methodThrow = methodThrows[i];
            builder.append(methodThrow.isClass().getQualifiedSourceName());
            if (i != methodThrows.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private String generateParameterSignature(JParameter[] parameters) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            JParameter parameter = parameters[i];
            result
                    .append(parameter.getType().getSimpleSourceName())
                    .append(" ")
                    .append(parameter.getName());
            if (i != parametersLength - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    private String generateParameters(JParameter[] parameters) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            JParameter parameter = parameters[i];
            result.append(parameter.getName());
            if (i != parametersLength - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }


}
