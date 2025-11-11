import React from "react";
import { ScrollView, View, Text, Pressable } from "react-native";
import { router } from "expo-router";
import { Ionicons } from "@expo/vector-icons";
import SecaoHeader from "../../components/home/secaoHeader";
import CategoryButton from "../../components/home/categoryButton";
import CourseCard from "../../components/home/courseCard";
import CourseCardHorizontal from "../../components/home/courseCardHorizontal";
import ContinueCard from "../../components/home/continueCard";

const continueCourse = {
  id: "ddm",
  title: "Digital Design Masterclass",
  instructor: "Nome do instrutor",
  progress: 40,
  image:
    "https://images.pexels.com/photos/326514/pexels-photo-326514.jpeg",
};

const categories = [
  { id: "ca1", name: "Design" },
  { id: "ca2", name: "Lógica de programação" },
  { id: "ca3", name: "Banco de dados" },
  { id: "ca4", name: "JavaScript" },
  { id: "ca5", name: "Java" },
  { id: "ca6", name: "Redes" },
];

const recommended = [
  {
    id: "r1",
    title: "React do básico ao avançado",
    image:
      "https://images.pexels.com/photos/29459444/pexels-photo-29459444.jpeg",
  },
  {
    id: "r2",
    title: "Git & Github: Guia Prático",
    image:
      "https://images.pexels.com/photos/4816921/pexels-photo-4816921.jpeg",
  },
  {
    id: "r3",
    title: "Metodologia Ágil e sua importância",
    image:
      "https://assets.dio.me/jZLJyiy2O9Zrr2TFB0qHbHUXHqG-E8-cngUR-Fu9qN0/f:webp/q:80/L2FydGljbGVzL2NvdmVyLzY0MGRkMTBkLTg1YzUtNDQ1MC1iNjBlLTc1YWU1M2I5OGZmYS5qcGc",
  },
  {
    id: "r4",
    title: "Fundamentos de JavaScript",
    image:
      "https://images.pexels.com/photos/1089440/pexels-photo-1089440.jpeg",
  },
];

const trending = [
  {
    id: "t1",
    title: "UX Research",
    author: "Nome do instrutor",
    image:
      "https://plus.unsplash.com/premium_photo-1733306548826-95daff988ae6?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1212",
  },
  {
    id: "t2",
    title: "Python",
    author: "Nome do instrutor",
    image:
      "https://images.pexels.com/photos/27427258/pexels-photo-27427258.jpeg",
  },
  {
    id: "t3",
    title: "Photoshop do zero",
    author: "Nome do instrutor",
    image:
      "https://logodownload.org/wp-content/uploads/2019/10/adobe-photoshop-logo-0.png",
  },
];

export default function Home() {
  const goToCourse = (id) => {
    router.push("/allCourses");
  };

  return (
    <ScrollView
      className="flex-1 bg-custom-primary"
      contentContainerStyle={{ paddingBottom: 28 }}
      keyboardShouldPersistTaps="handled">

      <SecaoHeader title="Continue seu aprendizado!"/>
      <ContinueCard data={continueCourse} onPress={() => goToCourse(continueCourse.id)}/>

      <SecaoHeader title="Categorias" onSeeAll={() => router.push("/allCourses")} />
      <ScrollView horizontal showsHorizontalScrollIndicator={false} className="px-6">
        {categories.map((c) => (
          <CategoryButton key={c.id} label={c.name}/>
        ))}
      </ScrollView>

      <SecaoHeader title="Cursos recomendados" onSeeAll={() => router.push("/allCourses")}/>
      <View className="px-6 flex-row flex-wrap justify-between">
        {recommended.map((item) => (
          <CourseCard key={item.id} item={item} onPress={() => goToCourse(item.id)}/>
        ))}
      </View>

      <SecaoHeader title="Em alta" onSeeAll={() => router.push("/allCourses")}/>
      <ScrollView horizontal showsHorizontalScrollIndicator={false} className="px-6">
        {trending.map((item) => (
          <CourseCardHorizontal key={item.id} item={item} onPress={() => goToCourse(item.id)}/>
        ))}
      </ScrollView>

      <Pressable onPress={() => router.push("/allCourses")} className="mx-6 mt-8 mb-2 flex-row items-center">
        <Ionicons name="paper-plane-outline" size={20} color="#fff"/>
        <Text className="text-white underline ml-2">Acesse todos os cursos!</Text>
      </Pressable>
    </ScrollView>
  );
}
