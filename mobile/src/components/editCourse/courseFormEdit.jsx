import React from "react";
import { Text, View, Pressable } from "react-native";
import { Ionicons } from '@expo/vector-icons';
import FormInput from "../inputs/formInput";
import SelectInput from "../inputs/selectInput";
import TextBoxInput from "../inputs/textBoxInput";

export default function courseFormEdit({ control, courses, onEditCourse }) {
    return (
        <>
            <FormInput control={control} name="nameCourse" label="Nome do curso" placeholder={courses?.name?.name} />
            <FormInput control={control} name="category" label="Categoria" placeholder={courses?.category?.name} />
            <SelectInput control={control} name="level" label="Nível" placeholder={courses?.level?.name} />
            <TextBoxInput control={control} name="description" label="Descrição" placeholder={courses?.description} />
            <View className="flex-1 items-end">
                <Pressable className="flex-row items-center gap-4" onPress={onEditCourse}>
                    <Ionicons name="download-outline" size={20} color="green" />
                    <Text className="text-green-500 text-lg">Salvar alterações</Text>
                </Pressable>
            </View>
        </>
    )
}