import { Tabs } from "expo-router";
import { MaterialIcons, Ionicons, FontAwesome } from '@expo/vector-icons';

export default function Layout() {
    return (
        <Tabs
            screenOptions={{
                tabBarStyle: {
                    backgroundColor: "#0E0025",
                    borderTopColor: "#1A1A2E",
                    borderTopWidth: 1,
                },
                tabBarActiveTintColor: "#ffffff",
                tabBarInactiveTintColor: "#999999",
            }}
        >
            <Tabs.Screen
                name="index"
                options={{
                    headerShown: false,
                    tabBarLabel: "Início",
                    tabBarIcon: ({ color, size }) => (
                        <MaterialIcons name="home" color={color} size={size} />
                    ),
                }} />
            <Tabs.Screen
                name="allCourses"
                options={{
                    headerShown: false,
                    tabBarLabel: "Pesquisar",
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="search" color={color} size={size} />
                    )
                }} />
            <Tabs.Screen
                name="createCourse"
                options={{
                    headerShown: false,
                    tabBarLabel: "Criar cursos",
                    tabBarIcon: ({ color, size }) => (
                        <FontAwesome name="plus-circle" color={color} size={size} />
                    ),

                }} />
            <Tabs.Screen
                name="myCourses"
                options={{
                    headerShown: false,
                    tabBarLabel: "Meus Cursos",
                    tabBarIcon: ({ color, size }) => (
                        <MaterialIcons name="menu-book" color={color} size={size} />
                    ),
                }} />
            <Tabs.Screen
                name="settings"
                options={{
                    headerShown: false,
                    tabBarLabel: "Configurações",
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="settings-sharp" color={color} size={size} />
                    ),
                }} />
        </Tabs>
    )
}