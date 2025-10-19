import react from "react";
import { Text, Pressable, KeyboardAvoidingView, Platform } from "react-native";
import { createCourseSchema } from "../../schemas/createCourse";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { router } from "expo-router";
import FormInput from "../../components/inputs/formInput";
import SelectInput from "../../components/inputs/selectInput";
import { ScrollView } from "react-native";
import TextBoxInput from "../../components/inputs/textBoxInput";

export default function CreateCourses() {
  const { control, handleSubmit } = useForm({
    resolver: zodResolver(createCourseSchema)
  })

  function onSubmit(data) {
    console.log(data);
    router.replace("/home");
  }

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "height"}
    >
      <ScrollView className="flex-1 px-6 bg-custom-primary"
        contentContainerStyle={{ justifyContent: "center", flexGrow: 1 }}>
          
        <Text className='text-white text-4xl text-center p-8'>Criar Curso
        </Text>

        <FormInput control={control} name="nameCourse" label="Nome do Curso" placeholder="Curso de Python" />
        <FormInput control={control} name="category" label="Categoria" placeholder="e.g., design, python, excel" />
        <SelectInput control={control} name="level" label="Nível" />
        <TextBoxInput control={control} name="description" label="Descrição" placeholder="Descrição do curso..." />

        <Pressable
          className="bg-white py-3 rounded-full mb-6"
          onPress={handleSubmit(onSubmit)}
        >
          <Text className="text-black text-2xl text-center font-bold">Criar Curso</Text>
        </Pressable>

        <Text className='text-white text-4xl text-center p-8'>Adicionar Aula
        </Text>

        <FormInput control={control} name="nameLesson" label="Nome da Aula" placeholder="Aula de Python" />
        <FormInput control={control} name="link" label="Link da Aula" placeholder="link.com.br" />
        <TextBoxInput control={control} name="descriptionL" label="Descrição" placeholder="Descrição da Aula..." />

        <Pressable
          className="bg-white py-3 rounded-full mb-6"
          onPress={handleSubmit(onSubmit)}
        >
          <Text className="text-black text-2xl text-center font-bold">Adicionar Aula</Text>
        </Pressable>

        <Pressable
          className="border border-white py-3 rounded-full mb-6"
          onPress={handleSubmit(onSubmit)}
        >
          <Text className="text-white text-2xl text-center font-bold">Remover Aula</Text>
        </Pressable>
  
      </ScrollView>
    </KeyboardAvoidingView>
  );
}