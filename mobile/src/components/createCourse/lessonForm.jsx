import React from "react";
import { Text } from "react-native";
import FormInput from "../inputs/formInput";
import TextBoxInput from "../inputs/textBoxInput";

export default function LessonForm({ control, index }) {
  return (
    <>
      <Text className='text-white text-xl text-start py-2'>Aula {index + 1}</Text>
      <FormInput control={control} name={`lessons.${index}.nameLesson`} label="Nome da Aula" placeholder="Aula de Python" />
      <FormInput control={control} name={`lessons.${index}.link`} label="Link da Aula" placeholder="www.youtube.com/watch?v=*********" />
      <TextBoxInput control={control} name={`lessons.${index}.descriptionL`} label="Descrição" placeholder="Descrição da Aula..." />
    </>
  );
}
