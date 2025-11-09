import React, { useState } from "react";
import { View, Text, Pressable, Modal } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import RadioButtonItem from "../inputs/radioButtonItem";
export default function FilterDrawer({ selectedCategory, setSelectedCategory }) {
    const [isOpen, setIsOpen] = useState(false);
    const categories = [
        { slug: "contributions", name: "Contribuições" },
        { slug: "finished", name: "Finalizados" },
        { slug: "in_progress", name: "Em Andamento" }];

    return (
        <>
            <Pressable className="h-8 w-8 flex justify-center items-center rounded-lg" onPress={() => setIsOpen(true)}>
                <Ionicons name="filter-outline" size={20} color="white" />
            </Pressable>

            <Modal transparent visible={isOpen} animationType="fade" onRequestClose={() => setIsOpen(false)}>
                <Pressable onPress={() => setIsOpen(false)} className="flex-1 bg-black/30" />

                <View className="absolute top-16 right-2 bg-custom-primary rounded-lg p-4 shadow-md w-72 h-4/5">
                    <View className="flex-row justify-between items-center mb-4">
                        <Text className="text-white font-bold mb-2 text-lg">Filtrar por:</Text>
                        <Pressable className='h-10 w-10 rounded-full flex items-center justify-center' onPress={() => setIsOpen(false)}>
                            <Ionicons name="close" size={20} color="white" />
                        </Pressable>
                    </View>
                    <Text className="text-white text-base pl-3 mb-2">Categorias:</Text>
                    <RadioButtonItem name="Todos os cursos" value={null} selectedValue={selectedCategory} onPress={() => { setSelectedCategory(null); setIsOpen(false); }} />

                    {categories.map((category) => (
                        <RadioButtonItem key={category.slug} name={category.name} value={category.slug} selectedValue={selectedCategory} onPress={() => { setSelectedCategory(category.slug); setIsOpen(false); }} />
                    ))}
                </View>
            </Modal>
        </>
    );
}
