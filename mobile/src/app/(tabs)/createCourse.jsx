import react, { useState } from "react";
import { Text, Pressable, KeyboardAvoidingView, Platform, View, ActivityIndicator } from "react-native";
import { createCourseSchema } from "../../schemas/createCourse";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { router } from "expo-router";
import { ScrollView } from "react-native";
import LessonForm from "../../components/createCourse/lessonForm";
import FormInput from "../../components/inputs/formInput";
import SelectInput from "../../components/inputs/selectInput";
import TextBoxInput from "../../components/inputs/textBoxInput";
import { createCourseRequest } from "../../requests/courses/createCourseRequest";
import Toast from "react-native-toast-message";

export default function CreateCourses() {
  const [loading, setLoading] = useState(false);
  const [lessons, setLessons] = useState([0]);
  const { control, handleSubmit, getValues, setValue, reset } = useForm({
    resolver: zodResolver(createCourseSchema),
    defaultValues: {
      lessons: []
    }
  })

  async function onSubmit(data) {
    setLoading(true);
    const response = await createCourseRequest(data);
    setLoading(false);

    if (!response.success) {
      Toast.show({
        type: "error",
        text1: "Erro ao criar o curso",
        text2: response.error,
        position: "bottom",
      });
      return;
    }
    reset();
    setLessons([0]);
    router.push("(tabs)/allCourses");
  }

  function addLesson() {
    setLessons((prevLessons) => [...prevLessons, prevLessons.length]);
  }

  function removeLesson() {
    if (lessons.length > 1) {
      const lastIndex = lessons.length - 1;

      const currentLessons = getValues('lessons') || [];

      const updatedLessons = currentLessons.slice(0, lastIndex);

      setValue('lessons', updatedLessons, { shouldValidate: false });

      setLessons((prevLessons) => prevLessons.slice(0, -1));
    }
  }

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "height"}
    >
      <ScrollView className="flex-1 px-6 bg-custom-primary"
        contentContainerStyle={{ justifyContent: "center", flexGrow: 1 }}>

        <Text className='text-white text-4xl text-center py-4'>Criar Curso</Text>

        <FormInput control={control} name="nameCourse" label="Nome do Curso" placeholder="Curso de Python" />
        <FormInput control={control} name="category" label="Categoria" placeholder="e.g., design, python, excel" />
        <SelectInput control={control} name="level" label="Nível" />
        <TextBoxInput control={control} name="description" label="Descrição" placeholder="Descrição do curso..." />

        {lessons.map((_, index) => (
          <LessonForm key={index} control={control} index={index} />
        ))}

        <View className="flex-1 flex-row justify-around">
          <Pressable className="bg-white p-3 rounded-full mb-6" onPress={addLesson}>
            <Text className="text-black text-xl text-center font-bold">Adicionar Aula</Text>
          </Pressable>

          <Pressable className="border border-white p-3 rounded-full mb-6" onPress={removeLesson}>
            <Text className="text-white text-xl text-center font-bold">Remover Aula</Text>
          </Pressable>
        </View>

        <Pressable className="bg-white py-3 rounded-full mb-6" onPress={handleSubmit(onSubmit)}>
          {loading ? (
            <ActivityIndicator size="small" color="#000" />
          ) : (
            <Text className="text-black text-2xl text-center font-bold">Criar Curso</Text>
          )}
        </Pressable>

      </ScrollView>
    </KeyboardAvoidingView>
  );
}