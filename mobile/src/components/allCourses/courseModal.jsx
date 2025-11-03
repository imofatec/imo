import React from "react";
import { View, Text, Image, Dimensions, Pressable, Modal } from "react-native";
import { Ionicons } from '@expo/vector-icons';

export default function CourseModal({ visible, onClose, nome, idImg, descricao,onHandleClick }) {
    const { width } = Dimensions.get("window");
    const imageWidth = width / 2 - 24;
    const imageHeight = imageWidth * 9 / 16;
    return (
        <Modal transparent={true} animationType="fade" visible={visible} onRequestClose={onClose}>
            <View className="flex-1 items-center justify-center bg-black/60">
                <View className="bg-custom-primary flex justify-center items-center gap-1 rounded-xl p-6 w-4/5 ">
                    <Pressable
                        className='right-0 top-0 absolute z-30 h-10 w-10 rounded-full flex items-center justify-center'
                        onPress={onClose}
                    >
                        <Ionicons name="close" size={20} color="white" />
                    </Pressable>
                    <Text className="text-white text-lg font-bold mb-4">{nome}</Text>
                    <Image
                        source={{
                            uri: `https://img.youtube.com/vi/${idImg}/maxresdefault.jpg`,
                        }}
                        style={{
                            width: imageWidth,
                            height: imageHeight,
                            borderRadius: 8,
                            borderWidth: 1,
                            borderColor: "#FFF",
                        }}
                        resizeMode="cover"
                    />
                    <Text className="mb-4 text-gray-200">{descricao}</Text>
                    <Pressable
                        className="bg-white rounded-full px-4 py-2 w-full"
                        onPress={onHandleClick}
                    >
                        <Text className="text-black text-center">Iniciar</Text>
                    </Pressable>
                </View>
            </View>
        </Modal>
    );
}