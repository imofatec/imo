import React from "react";
import { Text, View, Pressable } from "react-native";
import FormInput from "../inputs/formInput";
import TextBoxInput from "../inputs/textBoxInput";

export default function LessonForm({ control, index, onRemove, canRemove }) {
  return (
    <>
      <View className="mb-6 p-4 rounded-2xl bg-white/5 border border-white/10">
      <View className="flex-row items-center justify-between mb-2">
      <Text className="text-white text-xl font-bold">Aula {index + 1}</Text>

      {canRemove && (
        <Pressable
          onPress={onRemove}
          className="px-3 py-1 rounded-full bg-red-500/90"
        >
      <Text className="text-white font-bold">Remover</Text>
        </Pressable>
        )}
      </View>
      </View>
      <Text className='text-white text-xl text-start py-2'>Aula {index + 1}</Text>
      <FormInput control={control} name={`lessons.${index}.nameLesson`} label="Nome da Aula" placeholder="Aula de Python" />
      <FormInput control={control} name={`lessons.${index}.link`} label="Link da Aula" placeholder="www.youtube.com/watch?v=*********" />
      <TextBoxInput control={control} name={`lessons.${index}.descriptionL`} label="Descrição" placeholder="Descrição da Aula..." />

    </>
  );
}
