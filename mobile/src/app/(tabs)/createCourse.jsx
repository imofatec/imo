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
          
        <Text className='text-white text-4xl text-center p-10'>Criar Curso
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
      </ScrollView>
    </KeyboardAvoidingView>
  );
}