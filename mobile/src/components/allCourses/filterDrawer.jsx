import React, { useState } from "react";
import { View, Text, Pressable, Modal } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useAllCategories } from "../../hooks/useAllCategories";

export default function FilterDrawer() {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const { categories, loading, error } = useAllCategories();

    return (
        <>
            <Pressable className="h-8 w-8 flex justify-center items-center rounded-lg" onPress={() => setIsOpen(true)}>
                <Ionicons name="filter-outline" size={20} color="white" />
            </Pressable>

            <Modal transparent visible={isOpen} animationType="fade" onRequestClose={() => setIsOpen(false)}>
                <Pressable
                    onPress={() => setIsOpen(false)}
                    className="flex-1 bg-black/30"
                />

                <View className="absolute top-16 right-2 bg-custom-primary rounded-lg p-4 shadow-md w-72 h-4/5">
                    <View className="flex-row justify-between items-center mb-4">
                        <Text className="text-white font-bold mb-2 text-lg">Filtrar por:</Text>
                        <Pressable className='h-10 w-10 rounded-full flex items-center justify-center' onPress={() => setIsOpen(false)}>
                            <Ionicons name="close" size={20} color="white" />
                        </Pressable>
                    </View>

                    <Text className="text-white text-base pl-3 mb-2">Categorias:</Text>

                    {loading && <Text className="text-white mt-2">Carregando categorias...</Text>}
                    {error && <Text className="text-red-500 mt-2">{error}</Text>}

                    {!loading && !error && (
                        <>
                            <Pressable
                                className={`flex-row items-center py-2 px-3 mb-2 rounded-lg ${selectedCategory === null ? "bg-blue-600" : "bg-custom-secondary"}`}
                                onPress={() => setSelectedCategory(null)}
                            >
                                <View className={`h-4 w-4 mr-3 rounded-full border-2 border-white flex items-center justify-center`}>
                                    {selectedCategory === null && <View className="h-2 w-2 bg-white rounded-full" />}
                                </View>
                                <Text className="text-white">Todos os cursos</Text>
                            </Pressable>

                            {categories.map((category) => (
                                <Pressable
                                    key={category.slug}
                                    className={`flex-row items-center py-2 px-3 mb-2 rounded-lg ${selectedCategory === category.slug
                                        ? "bg-blue-600"
                                        : "bg-custom-secondary"
                                        }`}
                                    onPress={() => setSelectedCategory(category.slug)}
                                >
                                    <View className={`h-4 w-4 mr-3 rounded-full border-2 border-white flex items-center justify-center`}>
                                        {selectedCategory === category.slug && (
                                            <View className="h-2 w-2 bg-white rounded-full" />
                                        )}
                                    </View>
                                    <Text className="text-white">{category.name}</Text>
                                </Pressable>
                            ))}
                        </>
                    )}
                </View>
            </Modal>
        </>
    );
}
