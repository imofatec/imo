import React from "react";
import { Text, View, Pressable } from "react-native";
import FormInput from "../inputs/formInput";
import TextBoxInput from "../inputs/textBoxInput";

export default function LessonForm({ control, index,namePlaceholder="Aula de Python",LinkPlaceholder="www.youtube.com/watch?v=*********",descPlaceholder="Descrição da Aula..."}) {
  return (
    <>
      <View className="p-2 mb-2 pl-4 rounded-2xl bg-white/5 border border-white/10">
        <View className="flex-row items-center justify-between">
          <Text className='text-white text-xl text-start'>Aula {index + 1}</Text>
        </View>
      </View>
      <FormInput control={control} name={`lessons.${index}.nameLesson`} label="Nome da Aula" placeholder={namePlaceholder} />
      <FormInput control={control} name={`lessons.${index}.link`} label="Link da Aula" placeholder={LinkPlaceholder} />
      <TextBoxInput control={control} name={`lessons.${index}.descriptionL`} label="Descrição" placeholder={descPlaceholder} />
    </>
  );
}
