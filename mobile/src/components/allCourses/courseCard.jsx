import React, { useState } from "react";
import { router } from "expo-router";
import { View, Text, Image, Dimensions, Pressable} from "react-native";
import CourseModal from "./courseModal";

export default function CourseCard({ nome, idImg, descricao,nomeSlug,courseID }) {
    const [modalVisible, setModalVisible] = useState(false);
    const { width } = Dimensions.get("window");
    const imageWidth = width / 2 - 24;
    const imageHeight = imageWidth * 9 / 16;

    function handleClick() {
        setModalVisible(false);
        router.push({
            pathname:  '/watch',
            params: {
                courseID: courseID,
                courseSlug: nomeSlug,
            },
        });
    }

    return (
        <View className="flex-1 items-center justify-center my-2">
            <Pressable onPress={() => setModalVisible(true)}>
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
            </Pressable>
            <Text className="text-white text-center mt-2" numberOfLines={2}>
                {nome}
            </Text>

            <CourseModal visible={modalVisible} onClose={() => setModalVisible(false)} nome={nome} idImg={idImg} descricao={descricao} onHandleClick={handleClick}/>
                
        </View>
    );
}
